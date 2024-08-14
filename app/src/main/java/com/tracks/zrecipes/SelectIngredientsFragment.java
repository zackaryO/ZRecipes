// java/com/tracks/zrecipes/SelectIngredientsFragment.java
package com.tracks.zrecipes;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectIngredientsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectIngredientsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View root;
    private String cuisine="";
    private Button btnSearch, btnClear, btnLog, btnSaved, btnLogout;
    Spinner proteinSpinner, herbsSpinner, fruitsAndVegSpinner, starchSpinner, cuisineSpinner;
    ImageView imgProtein, imgStarch, imgVeg, imgSpice;
    TextView selectedIngredientsText;
    List<String> allSelectedIngredientsList;
    List<String> allSelectedIngredients;

    List<String> selectedIngredients = new ArrayList<>();
    private SelectIngredientsListener mCallBack;
    private EditText eTXT;
    private Button btnAdd;

    public interface SelectIngredientsListener {
        void GetIngredients(String ingredients);
        void Login();
        void SavedFragment();
        void Logout();
    }

    //    private String selectedIngredientsString;
    private String allSelectedIngredientsString;
    // Create the lists of food ingredientsBeef
    String[] proteins = new String[]{"", "Beef", "Chicken", "Crab", "Clams", "Cheese", "Duck", "Eggs",
            "Fish", "Ham", "Lamb", "Lobster", "Mussels", "Octopus", "Pork", "Quail", "Salmon", "Sardines",
            "Sausage", "Scallops", "Shrimp", "Squid", "Tofu", "Turkey", "Veal", "Venison"};
    String[] herbs = new String[]{"", "Basil", "Bay leaves", "Black pepper", "Cayenne", "Chives",
            "Cilantro", "Cinnamon", "Cloves", "Coriander", "Dill", "Ginger", "Mint", "Nutmeg", "Oregano",
            "Paprika", "Parsley", "Rosemary", "Sage", "Thyme", "Turmeric"};
    String[] fruitsAndVeggies = new String[]{"", "Apples", "Avocado", "Bananas", "Berries", "Broccoli",
            "Cabbage", "Carrots", "Cauliflower", "Celery", "Cilantro", "Cucumbers", "Eggplant", "Grapes",
            "Kale", "Lettuce", "Mangoes", "Melons", "Mushrooms", "Onions", "Oranges", "Peaches", "Pears",
            "Peppers", "Pineapple", "Potatoes", "Pumpkin", "Spinach", "Squash", "Tomatoes", "Zucchini",
            "Peas", "Corn", "Quinoa", "Rice", "Barley", "Oats", "Wheat", "Couscous", "Buckwheat", "Millet",
            "Lentils", "Chickpeas", "Black beans", "Kidney beans", "Lima beans"};
    String[] starches = new String[]{"", "Angel hair pasta", "Bread", "Couscous", "Egg noodles", "Farfalle",
            "Fettuccine", "Fusilli", "Gnocchi", "Lasagna", "Linguine", "Macaroni", "Orzo", "Penne",
            "Polenta", "Potatoes", "Quinoa", "Ramen noodles", "Rice", "Rigatoni", "Rotini", "Soba noodles",
            "Spaghetti", "Tagliatelle", "Tortellini", "Udon noodles", "Wild rice"};

    String[] cuisines = new String[]{"", "African", "Asian", "American", "British", "Cajun", "Caribbean",
            "Chinese", "Eastern European", "European", "French", "German", "Greek", "Indian", "Irish",
            "Italian", "Japanese", "Jewish", "Korean", "Latin American", "Mediterranean", "Mexican",
            "Middle Eastern", "Nordic", "Southern", "Spanish", "Thai", "Vietnamese"};


    public SelectIngredientsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectIngredientsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectIngredientsFragment newInstance(String param1, String param2) {
        SelectIngredientsFragment fragment = new SelectIngredientsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        try {
            mCallBack = (SelectIngredientsListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement SelectIngredientsListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_select_ingredients, container, false);
        setHasOptionsMenu(true);

        //load images
        imgProtein = root.findViewById(R.id.imgProtein);
        imgStarch = root.findViewById(R.id.imgStarch);
        imgVeg = root.findViewById(R.id.imgVeg);
        imgSpice = root.findViewById(R.id.imgSpice);
        //String proteinUrl = "https://thumbs.dreamstime.com/z/raw-meats-collection-board-67834263.jpg";
        // Picasso.get().load(proteinUrl).into(imgProtein);
//        String spiceUrl = "https://thumbs.dreamstime.com/z/various-herbs-spices-black-stone-plate-57018503.jpg";
//        Picasso.get().load(spiceUrl).into(imgSpice);
//        String vegUrl = "https://thumbs.dreamstime.com/z/healthy-food-vegan-vegetarian-nutrition-" +
//                "concept-clean-eating-diet-variety-vegetables-fruits-legumes-cereals-seeds-166206756.jpg";
//        Picasso.get().load(vegUrl).into(imgVeg);
//        String starchUrl = "https://thumbs.dreamstime.com/z/pasta-rice-half-half-84546638.jpg";
//        Picasso.get().load(starchUrl).into(imgStarch);


        // Get references to the Spinners in the layout
        proteinSpinner = root.findViewById(R.id.protein_spinner);
        herbsSpinner = root.findViewById(R.id.herbs_spinner);
        fruitsAndVegSpinner = root.findViewById(R.id.fruits_and_veg_spinner);
        starchSpinner = root.findViewById(R.id.starch_spinner);
        cuisineSpinner = root.findViewById(R.id.cuisine_spinner);

        // Create ArrayAdapters to populate the Spinners with the lists of food ingredients
        ArrayAdapter<String> proteinAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, proteins);
        proteinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        proteinSpinner.setAdapter(proteinAdapter);

        ArrayAdapter<String> herbsAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, herbs);
        herbsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        herbsSpinner.setAdapter(herbsAdapter);

        ArrayAdapter<String> fruitsAndVegAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, fruitsAndVeggies);
        fruitsAndVegAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fruitsAndVegSpinner.setAdapter(fruitsAndVegAdapter);

        ArrayAdapter<String> starchAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, starches);
        starchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        starchSpinner.setAdapter(starchAdapter);


        ArrayAdapter<String> cuisineAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, cuisines);
        cuisineAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cuisineSpinner.setAdapter(cuisineAdapter);

// Declare a List to store all the selected ingredients
        allSelectedIngredients = new ArrayList<>();
        AdapterView.OnItemSelectedListener spinnerListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected ingredients from the Spinners

                selectedIngredients.add((String) proteinSpinner.getSelectedItem());
                selectedIngredients.add((String) herbsSpinner.getSelectedItem());
                selectedIngredients.add((String) fruitsAndVegSpinner.getSelectedItem());
                selectedIngredients.add((String) starchSpinner.getSelectedItem());
                cuisine = (String) cuisineSpinner.getSelectedItem();

                // Add the new selected ingredients to the existing list
                allSelectedIngredients.addAll(selectedIngredients);

                // Create a set to remove duplicate ingredients
                Set<String> allSelectedIngredientsSet = new HashSet<>(allSelectedIngredients);
                // Convert the set back to a list
                allSelectedIngredientsList = new ArrayList<>(allSelectedIngredientsSet);
                allSelectedIngredientsString = "";
                for (int i = 0; i < allSelectedIngredientsList.size(); i++) {
                    String ingredient = allSelectedIngredientsList.get(i);
                    if (!ingredient.isEmpty()) {
                        if (!allSelectedIngredientsString.isEmpty()) {
                            allSelectedIngredientsString += ", ";
                        }
                        allSelectedIngredientsString += ingredient;
                    }
                }

                // Display all the selected ingredients in the text field
                selectedIngredientsText.setText(allSelectedIngredientsString);

//                selectedIngredientsString = allSelectedIngredientsString;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        };
        proteinSpinner.setOnItemSelectedListener(spinnerListener);
        herbsSpinner.setOnItemSelectedListener(spinnerListener);
        fruitsAndVegSpinner.setOnItemSelectedListener(spinnerListener);
        starchSpinner.setOnItemSelectedListener(spinnerListener);
        cuisineSpinner.setOnItemSelectedListener(spinnerListener);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        selectedIngredientsText = root.findViewById(R.id.txtIngredList);
        btnSearch = root.findViewById(R.id.btnSearch);

        Log.d("cuisine", "allSelectedIngredientsString: " + allSelectedIngredientsString);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cuisine != "") {
                    allSelectedIngredientsString += "&cuisine=" + cuisine;
                    mCallBack.GetIngredients(allSelectedIngredientsString);
                    Log.d("cuisine", "allSelectedIngredientsString: " + allSelectedIngredientsString);
                }
                else{
                    mCallBack.GetIngredients(allSelectedIngredientsString);
                }
            }

        });
        btnClear = root.findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearIngredients();

            }
        });
        // for logging in to firebase
        btnLog = root.findViewById(R.id.btnLog);
//        btnLog.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mCallBack.Login();
//            }
//        });

//        btnSaved = root.findViewById(R.id.btnSaved);
//        btnSaved.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mCallBack.SavedFragment();
//            }
//        });
        // for logging out of firebase
//        btnLogout = root.findViewById(R.id.btnLogout);
//        btnLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mCallBack.Logout();
//            }
//        });
    }

    public void clearIngredients() {
        allSelectedIngredientsString = "";
        selectedIngredientsText.setText(allSelectedIngredientsString);
        proteinSpinner.setSelection(0);
        herbsSpinner.setSelection(0);
        fruitsAndVegSpinner.setSelection(0);
        starchSpinner.setSelection(0);
        cuisineSpinner.setSelection(0);
        selectedIngredients.clear();
        allSelectedIngredientsList.clear();
        allSelectedIngredients.clear();
    }
}