package com.example.wagbaproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.wagbaproject.databinding.ActivityRestAvailBinding;
import com.example.wagbaproject.databinding.ActivitySignUpBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class RestAvailActivity extends AppCompatActivity implements RestAvailInterface
{

    Button signOutBtn;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ArrayList<RestAvailModel> resList;
    RestAvailAdapter restaurantAdapter;
    DatabaseReference databaseReference;
    ActivityRestAvailBinding binding;
    //FirebaseDatabase database = FirebaseDatabase.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityRestAvailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        databaseReference = FirebaseDatabase.getInstance().getReference();
        resList = new ArrayList<RestAvailModel>();
        //initDatabase();


        binding.myProfileBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });

        databaseReference.child("restaurants").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                try {

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                    {
                        String restID = postSnapshot.getKey();
                        String restName = (String) postSnapshot.child("restName").getValue();
                        String kindOfFood = (String) postSnapshot.child("kindOfFood").getValue();
                        String image = (String) postSnapshot.child("image").getValue();

                        resList.add(new RestAvailModel(image,restName,kindOfFood, Integer.parseInt(restID)));
                    }
                    initRecyclerView();
                }

                catch (Exception e)
                {
                    Toast.makeText(RestAvailActivity.this, "Rest Avail " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("RT DB", "Failed to read value.", error.toException());
            }
        });

        //to sign out
        signOutBtn = findViewById(R.id.signOutBtn);
        signOutBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initDatabase()
    {
        //rest1
        ArrayList<RestDetailsModel> macdonaldsDishes = new ArrayList<RestDetailsModel>();
        macdonaldsDishes.add(new RestDetailsModel("https://www.rescuedogkitchen.com/wp-content/uploads/2021/11/IMG_3192.jpg","Big Mac","burger, tomato, sauce","60 LE",""));
        macdonaldsDishes.add(new RestDetailsModel("https://www.rescuedogkitchen.com/wp-content/uploads/2021/11/IMG_3192.jpg","Mac Royale","burger, sauce","70 LE",""));
        macdonaldsDishes.add(new RestDetailsModel("https://www.rescuedogkitchen.com/wp-content/uploads/2021/11/IMG_3192.jpg","Chicken Macdo","chicken burger, sauce","30 LE",""));
        macdonaldsDishes.add(new RestDetailsModel("https://www.rescuedogkitchen.com/wp-content/uploads/2021/11/IMG_3192.jpg","Cheese Burger","burger, cheese","30 LE",""));

        resList.add(new RestAvailModel("https://st2.depositphotos.com/2942339/11233/i/600/depositphotos_112334008-stock-photo-indianapolis-circa-march-2016-mcdonalds.jpg", "Macdonald's", "burger, fries", macdonaldsDishes));


        //rest2
        ArrayList<RestDetailsModel> KFCDishes = new ArrayList<RestDetailsModel>();
        KFCDishes.add(new RestDetailsModel("https://dynaimage.cdn.cnn.com/cnn/c_fill,g_auto,w_1200,h_675,ar_16:9/https%3A%2F%2Fcdn.cnn.com%2Fcnnnext%2Fdam%2Fassets%2F200522115738-20200522-kfc-chicken-sandwich.jpg","Mighty Love","2 Mighty Zinger Sandwiches + 2 Coleslaw + 1 L Drink","184 LE",""));
        KFCDishes.add(new RestDetailsModel("https://dynaimage.cdn.cnn.com/cnn/c_fill,g_auto,w_1200,h_675,ar_16:9/https%3A%2F%2Fcdn.cnn.com%2Fcnnnext%2Fdam%2Fassets%2F200522115738-20200522-kfc-chicken-sandwich.jpg","Supreme Love","2 supreme sandwich + 2 cole slaw + 1 litre pepsi","146 LE",""));
        KFCDishes.add(new RestDetailsModel("https://dynaimage.cdn.cnn.com/cnn/c_fill,g_auto,w_1200,h_675,ar_16:9/https%3A%2F%2Fcdn.cnn.com%2Fcnnnext%2Fdam%2Fassets%2F200522115738-20200522-kfc-chicken-sandwich.jpg","Mighty Plus","Mighty Zinger Sandwich + Rizo + Coleslaw+ Drink","131 LE",""));
        KFCDishes.add(new RestDetailsModel("https://dynaimage.cdn.cnn.com/cnn/c_fill,g_auto,w_1200,h_675,ar_16:9/https%3A%2F%2Fcdn.cnn.com%2Fcnnnext%2Fdam%2Fassets%2F200522115738-20200522-kfc-chicken-sandwich.jpg","Cheering Box","3 twister sandwiches, 6 pieces of chicken, family french fries and pepsi liter","283 LE",""));

        resList.add(new RestAvailModel("https://www.marefa.org/w/images/thumb/b/bf/KFC_logo.svg/1200px-KFC_logo.svg.png", "KFC", "chicken, fries", KFCDishes));


        //rest3
        ArrayList<RestDetailsModel> wimpyDishes = new ArrayList<RestDetailsModel>();
        wimpyDishes.add(new RestDetailsModel("https://scenenow.com/Content/editor_api/images/22886039_1817398594955192_7781693150556803975_n-edcaaf37-4b5b-4867-b04f-05e8f1648c6d.jpg","Old School Single","burger, cheese, sauce","50 LE",""));
        wimpyDishes.add(new RestDetailsModel("https://scenenow.com/Content/editor_api/images/22886039_1817398594955192_7781693150556803975_n-edcaaf37-4b5b-4867-b04f-05e8f1648c6d.jpg","Old School Double","burger, cheese, sauce","75 LE",""));
        wimpyDishes.add(new RestDetailsModel("https://scenenow.com/Content/editor_api/images/22886039_1817398594955192_7781693150556803975_n-edcaaf37-4b5b-4867-b04f-05e8f1648c6d.jpg","Old School Triple","burger, cheese, sauce","100 LE",""));
        wimpyDishes.add(new RestDetailsModel("https://scenenow.com/Content/editor_api/images/22886039_1817398594955192_7781693150556803975_n-edcaaf37-4b5b-4867-b04f-05e8f1648c6d.jpg","Jr. Chicky","burger, cheese, sauce","45 LE",""));

        resList.add(new RestAvailModel("https://upload.wikimedia.org/wikipedia/commons/thumb/1/1e/Logo_of_Wimpy.svg/1200px-Logo_of_Wimpy.svg.png", "Wimpy", "burger, fries", wimpyDishes));


        //rest4
        ArrayList<RestDetailsModel> cookDoorDishes = new ArrayList<RestDetailsModel>();
        cookDoorDishes.add(new RestDetailsModel("https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/f5e3eb80-6305-4e1b-96a3-8bff2c666d3d.jpg","Happiness Offer","2 large chicken sandwiches, 2 medium chicken sandwiches, 1 large fries, 1 coca cola liter","240 LE",""));
        cookDoorDishes.add(new RestDetailsModel("https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/f5e3eb80-6305-4e1b-96a3-8bff2c666d3d.jpg","Super Crunchy Chicken Sandwich","Spicy crispy golden chicken pieces, topped with Majorca sauce, pickled, turkey & melted mozzarella cheese","89 LE",""));
        cookDoorDishes.add(new RestDetailsModel("https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/f5e3eb80-6305-4e1b-96a3-8bff2c666d3d.jpg","Chicken Friday Sandwich","Marinated chicken thigh, tossed with marinated grilled mushroom, covered with melted mozzarella cheese, Majorca sauce, pickled & caramelized onion","83 LE",""));
        cookDoorDishes.add(new RestDetailsModel("https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/f5e3eb80-6305-4e1b-96a3-8bff2c666d3d.jpg","Super Grilled Viagra Sandwich","Grilled shrimp and grilled crab, viagra sauce, melted mozzarella cheese","99 LE",""));

        resList.add(new RestAvailModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRE-GLz4yAh6E16R2KoRKp1XvBubJa_GIkpfeaChEoeUFIdCcu8eNKlB2So31qrczmpKHs&usqp=CAU", "Cook Door", "chicken, beef, fries", cookDoorDishes));


        //rest5
        ArrayList<RestDetailsModel> momenDishes = new ArrayList<RestDetailsModel>();
        momenDishes.add(new RestDetailsModel("https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/b3a1d44e-3125-4085-b4f1-f2018fc06335.jpg","Combo El Akel Offer","Chicken magnum sandwich, medium fries, coleslaw salad and soft drink","140 LE",""));
        momenDishes.add(new RestDetailsModel("https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/b3a1d44e-3125-4085-b4f1-f2018fc06335.jpg","Family Box","4 sandwiches, 4 medium French fries, 1 liter of soft drink","341.20 LE",""));
        momenDishes.add(new RestDetailsModel("https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/b3a1d44e-3125-4085-b4f1-f2018fc06335.jpg","El Twin Offer","2 medium sandwiches, medium French fries and coca cola","124 LE",""));
        momenDishes.add(new RestDetailsModel("https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/b3a1d44e-3125-4085-b4f1-f2018fc06335.jpg","2 Sandwiches Offer","Large sandwich, medium sandwich, can, fries","148 LE",""));

        resList.add(new RestAvailModel("https://momen.com.eg/assets/img/logo4.png", "Mo'men", "chicken, beef, fries", momenDishes));


        //rest6
        ArrayList<RestDetailsModel> papaJohnsDishes = new ArrayList<RestDetailsModel>();
        papaJohnsDishes.add(new RestDetailsModel("https://www.papajohnsegypt.com/images/Products/Chicken-Ranch.jpg","Chicken Bbq","chicken, bbq sauce, mushrooms, onions","140 LE",""));
        papaJohnsDishes.add(new RestDetailsModel("https://www.papajohnsegypt.com/images/Products/Chicken-Ranch.jpg","Chicken Ranch","chicken, bbq sauce, mushrooms, tomato","150 LE",""));
        papaJohnsDishes.add(new RestDetailsModel("https://www.papajohnsegypt.com/images/Products/Chicken-Ranch.jpg","6 Cheese","mixed cheese","150 LE",""));
        papaJohnsDishes.add(new RestDetailsModel("https://www.papajohnsegypt.com/images/Products/Chicken-Ranch.jpg","Pepproni","pepproni, tomato sauce, cheese","130 LE",""));

        resList.add(new RestAvailModel("https://images.deliveryhero.io/image/talabat/restaurants/logo_(1)_637038116180008094.jpg?width=300", "Papa John's", "pizza",papaJohnsDishes));


        //rest7
        ArrayList<RestDetailsModel> pizzaHutDishes = new ArrayList<RestDetailsModel>();
        pizzaHutDishes.add(new RestDetailsModel("https://static.phdvasia.com/kw/menu/single/desktop_thumbnail_71f8836a-6b14-449e-9cf6-28a7900fd02b.jpg","Chicken Bbq","chicken, bbq sauce, mushrooms","170 LE",""));
        pizzaHutDishes.add(new RestDetailsModel("https://static.phdvasia.com/kw/menu/single/desktop_thumbnail_71f8836a-6b14-449e-9cf6-28a7900fd02b.jpg","Chicken Ranch","chicken, bbq sauce, mushrooms, tomato","160 LE",""));
        pizzaHutDishes.add(new RestDetailsModel("https://static.phdvasia.com/kw/menu/single/desktop_thumbnail_71f8836a-6b14-449e-9cf6-28a7900fd02b.jpg","Margarita","cheese, tomato sauce","150 LE",""));
        pizzaHutDishes.add(new RestDetailsModel("https://static.phdvasia.com/kw/menu/single/desktop_thumbnail_71f8836a-6b14-449e-9cf6-28a7900fd02b.jpg","Pepproni","pepproni, tomato sauce, cheese","170 LE",""));

        resList.add(new RestAvailModel("https://upload.wikimedia.org/wikipedia/commons/a/a0/Pizza_Hut_international_logo%2C_2008%E2%80%932016.svg", "Pizza Hut", "pizza", pizzaHutDishes));


        //rest8
        ArrayList<RestDetailsModel> pizzaStationDishes = new ArrayList<RestDetailsModel>();
        pizzaStationDishes.add(new RestDetailsModel("https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/daa517bf-7bfe-4791-a7b0-4665d0776995.jpg","Chicken Bbq","chicken, bbq sauce, mushrooms","170 LE",""));
        pizzaStationDishes.add(new RestDetailsModel("https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/daa517bf-7bfe-4791-a7b0-4665d0776995.jpg","Chicken Ranch","chicken, bbq sauce, mushrooms, tomato","160 LE",""));
        pizzaStationDishes.add(new RestDetailsModel("https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/daa517bf-7bfe-4791-a7b0-4665d0776995.jpg","Seafood","mixed seafood, cheese, ranch sauce","200 LE",""));
        pizzaStationDishes.add(new RestDetailsModel("https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/daa517bf-7bfe-4791-a7b0-4665d0776995.jpg","Pepproni","pepproni, tomato sauce, cheese","180 LE",""));

        resList.add(new RestAvailModel("https://play-lh.googleusercontent.com/1li_Je45-4eIgwXyEt3_BF1sddVRUafPaC1xUsADXPdo6aXB_eAobueLL8xvJeKwsHs", "Pizza Station", "pizza", pizzaStationDishes));


        //rest9
        ArrayList<RestDetailsModel> tikkaDishes = new ArrayList<RestDetailsModel>();
        tikkaDishes.add(new RestDetailsModel("https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/39c06f24-7234-4749-99f2-aac227208056.jpg","Chicken fillet breasts","chicken fillet breasts, fries, rice, salad","220 LE",""));
        tikkaDishes.add(new RestDetailsModel("https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/39c06f24-7234-4749-99f2-aac227208056.jpg","Chicken breast","chicken breast, rice, fries, salad","180 LE",""));
        tikkaDishes.add(new RestDetailsModel("https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/39c06f24-7234-4749-99f2-aac227208056.jpg","Chicken Kofta","chicken kofta, bread, rice, salad","190 LE",""));
        tikkaDishes.add(new RestDetailsModel("https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/39c06f24-7234-4749-99f2-aac227208056.jpg","Kebab","kebab, rice, bread, salad, fries","240 LE",""));

        resList.add(new RestAvailModel("https://cdn.hotlines.tel/imgs/hotlinesimgs/19099.jpg", "Tikka", "chicken, beef, fries, salads", tikkaDishes));


        //rest10
        ArrayList<RestDetailsModel> eldahanDishes = new ArrayList<RestDetailsModel>();
        eldahanDishes.add(new RestDetailsModel("https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/1217e0b9-d3f4-4490-82fe-8868991b3b95.jpg","Chicken fillet breasts","chicken fillet breasts, fries, rice, salad","220 LE",""));
        eldahanDishes.add(new RestDetailsModel("https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/1217e0b9-d3f4-4490-82fe-8868991b3b95.jpg","Chicken breast","chicken breast, rice, fries, salad","180 LE",""));
        eldahanDishes.add(new RestDetailsModel("https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/1217e0b9-d3f4-4490-82fe-8868991b3b95.jpg","Chicken Kofta","chicken kofta, bread, rice, salad","190 LE",""));
        eldahanDishes.add(new RestDetailsModel("https://s3-eu-west-1.amazonaws.com/elmenusv5-stg/Normal/1217e0b9-d3f4-4490-82fe-8868991b3b95.jpg","Kebab","kebab, rice, bread, salad, fries","240 LE",""));

        resList.add(new RestAvailModel("https://images.deliveryhero.io/image/talabat/restaurants/logo_637454801079452475.jpg?width=180", "Eldahan", "chicken, beef, salads, fries", eldahanDishes));

        databaseReference.setValue(resList);
    }


    private void initRecyclerView()
    {
        recyclerView = findViewById(R.id.recyclerViewRestAvail);
        linearLayoutManager = new LinearLayoutManager(RestAvailActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        restaurantAdapter = new RestAvailAdapter(resList, RestAvailActivity.this);
        recyclerView.setAdapter(restaurantAdapter);
    }

    @Override
    public void onItemClick(int position)
    {
        Intent intent = new Intent(RestAvailActivity.this, RestDetailsActivity.class);
        intent.putExtra("restName", resList.get(position).getRestName());
        intent.putExtra("restImage", resList.get(position).getImage());

        Log.d("restID", String.valueOf(resList.get(position).getRestID()));

        intent.putExtra("restID", resList.get(position).getRestID());
        startActivity(intent);
    }
}






