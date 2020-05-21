package au.edu.utas.sddhewa.assignment;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import au.edu.utas.sddhewa.assignment.db.Database;
import au.edu.utas.sddhewa.assignment.ui.create.raffle.CreateRaffle;
import au.edu.utas.sddhewa.assignment.ui.create.user.CreateUser;
import au.edu.utas.sddhewa.assignment.ui.home.Home;
import au.edu.utas.sddhewa.assignment.ui.sell.SellTicket;

/**
 * Main activity of the Raffle Application
 *
 * @author Sakna Hewa Galamulage
 * @date 05/05/2020
 * @updatedBy
 * @version 1
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private SQLiteDatabase db;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setSupportActionBar(toolbar);

        Database databaseConnection = new Database(this);
        db = databaseConnection.open();
        
        drawerLayout = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_draw_open, R.string.navigation_draw_close);
        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragment_container, new Home(db, getSupportFragmentManager(),
                            getApplicationContext())).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
           drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_container,
                                new Home(db, getSupportFragmentManager(), getApplicationContext()))
                        .addToBackStack("home").commit();
                navigationView.setCheckedItem(R.id.nav_home);
                break;
            case R.id.nav_create_raffle:
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_container,
                                new CreateRaffle(getApplicationContext(), db, getSupportFragmentManager(),
                                        getPackageManager()))
                        .addToBackStack("createRaffle").commit();
                navigationView.setCheckedItem(R.id.nav_create_raffle);
                break;
            case R.id.nav_sell_raffle:
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_container,
                                new SellTicket(getApplicationContext(), db, getSupportFragmentManager()))
                        .addToBackStack("sellTicket").commit();
                navigationView.setCheckedItem(R.id.nav_sell_raffle);
                break;
            case R.id.nav_create_customer:
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_container,
                                new CreateUser(getApplicationContext(), db, getSupportFragmentManager()))
                        .addToBackStack("createCustomer").commit();
                navigationView.setCheckedItem(R.id.nav_create_customer);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
