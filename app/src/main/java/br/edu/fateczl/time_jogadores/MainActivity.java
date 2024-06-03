package br.edu.fateczl.time_jogadores;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {
    private Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Bundle b = getIntent().getExtras();
        if (b != null) {
            loadFragment(b);
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new HomeFragment()).commit();
        }
    }

    private void loadFragment(Bundle b) {
        String typeFrag = b.getString("tipo");
        assert typeFrag != null;
        switch (typeFrag) {
            case "Home":
                fragment = new HomeFragment();
                break;
            case "Time":
                fragment = new TimeFragment();
                break;
            case "Jogador":
                fragment = new JogadorFragment();
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemID = item.getItemId();
        Bundle bundle = new Bundle();
        Intent i = new Intent(this, MainActivity.class);

        if (itemID == R.id.item_home) {
            bundle.putString("tipo","Home");
            i.putExtras(bundle);
            this.startActivity(i);
            this.finish();
            return true;
        }
        if (itemID == R.id.item_jogador) {
            bundle.putString("tipo","Jogador");
            i.putExtras(bundle);
            this.startActivity(i);
            this.finish();
            return true;
        }
        if (itemID == R.id.item_time) {
            bundle.putString("tipo","Time");
            i.putExtras(bundle);
            this.startActivity(i);
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}