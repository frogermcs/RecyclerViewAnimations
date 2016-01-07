package frogermcs.io.recyclerviewanimations;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.rvColors)
    RecyclerView rvColors;
    @Bind(R.id.rgOperations)
    RadioGroup rgOperations;
    @Bind(R.id.cbPredictive)
    CheckBox cbPredictive;
    @Bind(R.id.cbCustomAnimator)
    CheckBox cbCustomAnimator;

    private ColorsAdapter colorsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupColorsList();
    }

    private void setupColorsList() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean supportsPredictiveItemAnimations() {
                return cbPredictive.isChecked();
            }
        };
        rvColors.setLayoutManager(layoutManager);

        colorsAdapter = new ColorsAdapter(this);
        rvColors.setAdapter(colorsAdapter);

        setupRecyclerViewAnimator();
    }

    public void onColorsListItemClicked(View view) {
        int itemAdapterPosition = rvColors.getChildAdapterPosition(view);
        if (itemAdapterPosition == RecyclerView.NO_POSITION) {
            return;
        }

        int checkedRadioButton = rgOperations.getCheckedRadioButtonId();
        if (checkedRadioButton == R.id.rbDelete) {
            colorsAdapter.removeItemAtPosition(itemAdapterPosition);
        } else if (checkedRadioButton == R.id.rbAdd) {
            colorsAdapter.addItemAtPosition(itemAdapterPosition + 1);
        } else if (checkedRadioButton == R.id.rbChange) {
            colorsAdapter.changeItemAtPosition(itemAdapterPosition);
        }
    }

    @OnCheckedChanged(R.id.cbCustomAnimator)
    public void onCustomAnimatorCheckedChange() {
        setupRecyclerViewAnimator();
    }

    private void setupRecyclerViewAnimator() {
        if (cbCustomAnimator.isChecked()) {
            rvColors.setItemAnimator(new MyItemAnimator());
        } else {
            rvColors.setItemAnimator(new DefaultItemAnimator());
        }
    }
}
