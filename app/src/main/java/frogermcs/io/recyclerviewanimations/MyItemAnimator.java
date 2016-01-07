package frogermcs.io.recyclerviewanimations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Miroslaw Stanek on 06.01.2016.
 */
public class MyItemAnimator extends DefaultItemAnimator {

    HashMap<RecyclerView.ViewHolder, AnimatorInfo> animatorMap = new HashMap<>();

    @Override
    public boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder viewHolder) {
        return true;
    }

    @NonNull
    @Override
    public ItemHolderInfo recordPreLayoutInformation(@NonNull RecyclerView.State state,
                                                     @NonNull RecyclerView.ViewHolder viewHolder,
                                                     int changeFlags,
                                                     @NonNull List<Object> payloads) {
        ColorTextInfo colorTextInfo = new ColorTextInfo();
        colorTextInfo.setFrom(viewHolder);
        return colorTextInfo;
    }

    @NonNull
    @Override
    public ItemHolderInfo recordPostLayoutInformation(@NonNull RecyclerView.State state,
                                                      @NonNull RecyclerView.ViewHolder viewHolder) {
        ColorTextInfo colorTextInfo = new ColorTextInfo();
        colorTextInfo.setFrom(viewHolder);
        return colorTextInfo;
    }

    @Override
    public boolean animateChange(@NonNull RecyclerView.ViewHolder oldHolder,
                                 @NonNull final RecyclerView.ViewHolder newHolder,
                                 @NonNull ItemHolderInfo preInfo,
                                 @NonNull ItemHolderInfo postInfo) {
        final ColorsAdapter.ColorViewHolder holder = (ColorsAdapter.ColorViewHolder) newHolder;
        final ColorTextInfo preColorTextInfo = (ColorTextInfo) preInfo;
        final ColorTextInfo postColorTextInfo = (ColorTextInfo) postInfo;

        ObjectAnimator fadeToBlack = ObjectAnimator.ofArgb(holder.itemView, "backgroundColor", preColorTextInfo.color, Color.BLACK);
        ObjectAnimator fadeFromBlack = ObjectAnimator.ofArgb(holder.itemView, "backgroundColor", Color.BLACK, postColorTextInfo.color);
        AnimatorSet bgAnim = new AnimatorSet();
        bgAnim.playSequentially(fadeToBlack, fadeFromBlack);

        ObjectAnimator oldTextRotate = ObjectAnimator.ofFloat(holder.tvColor, View.ROTATION_X, 0, 90);
        ObjectAnimator newTextRotate = ObjectAnimator.ofFloat(holder.tvColor, View.ROTATION_X, -90, 0);
        oldTextRotate.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                holder.tvColor.setText(preColorTextInfo.text);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                holder.tvColor.setText(postColorTextInfo.text);
            }
        });
        AnimatorSet textAnim = new AnimatorSet();
        textAnim.playSequentially(oldTextRotate, newTextRotate);

        AnimatorSet overallAnim = new AnimatorSet();
        overallAnim.playTogether(bgAnim, textAnim);
        overallAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                dispatchAnimationFinished(newHolder);
                animatorMap.remove(newHolder);
            }
        });

        AnimatorInfo animatorInfo = animatorMap.get(newHolder);
        if (animatorInfo != null) {
            boolean firstHalf = animatorInfo.oldTextRotator.isRunning();
            if (firstHalf) {
                fadeToBlack.setCurrentPlayTime(animatorInfo.fadeToBlackAnim.getCurrentPlayTime());
                oldTextRotate.setCurrentPlayTime(animatorInfo.oldTextRotator.getCurrentPlayTime());
            } else {
                fadeToBlack.setCurrentPlayTime(animatorInfo.fadeToBlackAnim.getDuration());
                oldTextRotate.setCurrentPlayTime(animatorInfo.oldTextRotator.getDuration());
                newTextRotate.setCurrentPlayTime(animatorInfo.newTextRotator.getCurrentPlayTime());
                fadeFromBlack.setIntValues((Integer)animatorInfo.fadeFromBlackAnim.getAnimatedValue(), postColorTextInfo.color);
            }

            animatorInfo.overallAnim.cancel();
            animatorMap.remove(newHolder);
        }

        animatorMap.put(newHolder, new AnimatorInfo(
                overallAnim, fadeToBlack, fadeFromBlack, oldTextRotate, newTextRotate
        ));

        overallAnim.start();

        return super.animateChange(oldHolder, newHolder, preInfo, postInfo);
    }

    private class ColorTextInfo extends ItemHolderInfo {
        int color;
        String text;

        @Override
        public ItemHolderInfo setFrom(RecyclerView.ViewHolder holder) {
            if (holder instanceof ColorsAdapter.ColorViewHolder) {
                ColorsAdapter.ColorViewHolder colorViewHolder = (ColorsAdapter.ColorViewHolder) holder;
                color = ((ColorDrawable) colorViewHolder.itemView.getBackground()).getColor();
                text = (String) colorViewHolder.tvColor.getText();
            }
            return super.setFrom(holder);
        }
    }

    private class AnimatorInfo {
        Animator overallAnim;
        ObjectAnimator fadeToBlackAnim, fadeFromBlackAnim, oldTextRotator, newTextRotator;

        public AnimatorInfo(Animator overallAnim, ObjectAnimator fadeToBlackAnim, ObjectAnimator fadeFromBlackAnim,
                            ObjectAnimator oldTextRotator, ObjectAnimator newTextRotator) {
            this.overallAnim = overallAnim;
            this.fadeToBlackAnim = fadeToBlackAnim;
            this.fadeFromBlackAnim = fadeFromBlackAnim;
            this.oldTextRotator = oldTextRotator;
            this.newTextRotator = newTextRotator;
        }
    }


}
