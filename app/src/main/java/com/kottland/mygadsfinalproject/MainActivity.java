package com.kottland.mygadsfinalproject;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;

        import com.kottland.mygadsfinalproject.activities.BuyerActivity;
        import com.kottland.mygadsfinalproject.activities.MerchantActivity;

public class MainActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        setTitle(context.getText(R.string.home_act_title));

    }

    public void toBuyer(View view) {

        startActivity(new Intent(MainActivity.this, BuyerActivity.class));
    }

    public void toMerchant(View view) {
        startActivity(new Intent(MainActivity.this, MerchantActivity.class));

    }
}
