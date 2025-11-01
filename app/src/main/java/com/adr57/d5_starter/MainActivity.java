package com.adr57.d5_starter;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements RcvAdapter.OnItemClickListener, RcvAdapter.OnLikeClickListener {

    private RecyclerView recyclerView;
    private RcvAdapter adapter;
    private List<CardItem> cardItems;

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

        setupToolbar();
        setupRecyclerView();
        generateSampleData();
    }

    private void setupToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Pinterest Demo");
        }
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);

        // Create StaggeredGridLayoutManager with 2 columns
//        StaggeredGridLayoutManager layoutManager = ....
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        // Set gap strategy for better performance
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);

//        set layout manager for recycler view
        recyclerView.setLayoutManager(layoutManager);
//        code here

        // Add item decoration for spacing
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 1, 10, true));

        // create data list
        cardItems = new ArrayList<>();

        // create adapter with data list
        adapter = new RcvAdapter(cardItems, this, this);

        // set adapter for recycler view
        recyclerView.setAdapter(adapter);
    }

    private void generateSampleData() {
        String[] sampleImages = {
                "https://picsum.photos/300/400", // portrait
                "https://picsum.photos/300/300", // square
                "https://picsum.photos/300/500", // tall portrait
                "https://picsum.photos/300/200", // landscape
                "https://picsum.photos/300/350",
                "https://picsum.photos/300/450",
                "https://picsum.photos/300/250",
                "https://picsum.photos/300/600",
                "https://picsum.photos/300/320",
                "https://picsum.photos/300/480",
                "https://picsum.photos/300/400",
                "https://picsum.photos/300/300",
                "https://picsum.photos/300/500",
                "https://picsum.photos/300/200",
                "https://picsum.photos/300/350",
                "https://picsum.photos/300/450",
                "https://picsum.photos/300/250",
                "https://picsum.photos/300/600",
                "https://picsum.photos/300/320",
                "https://picsum.photos/300/480"
        };

        String[] sampleTitles = {
                "Beautiful Landscape",
                "Urban Architecture",
                "Nature Photography",
                "Modern Design",
                "Art Inspiration",
                "Travel Destination",
                "Food Photography",
                "Fashion Style",
                "Home Decor",
                "Creative Ideas"
        };

        String[] sampleDescriptions = {
                "This is an amazing photo that captures the essence of natural beauty.",
                "Modern architecture at its finest with clean lines and innovative design.",
                "A stunning view that will take your breath away with its vibrant colors.",
                "Perfect composition and lighting make this image truly special.",
                "Inspirational content that will spark your creativity and imagination.",
                "Discover new places and experiences through this captivating image.",
                "Delicious food presentation that will make your mouth water instantly.",
                "Trendy fashion styles that combine comfort and elegance perfectly.",
                "Beautiful home decoration ideas for modern living spaces.",
                "Creative concepts that push the boundaries of traditional design."
        };

        cardItems.clear();
        Random random = new Random();

        for (int i = 1; i <= 20; i++) {
            String randomImage = sampleImages[i-1];
            String randomTitle = sampleTitles[random.nextInt(sampleTitles.length)];
            String randomDescription = sampleDescriptions[random.nextInt(sampleDescriptions.length)];

            cardItems.add(new CardItem(
                    i,
                    randomImage,
                    randomTitle + " #" + i,
                    randomDescription,
                    random.nextInt(1000) + 1,
                    i % 3 == 0 // Randomly set some as liked
            ));
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(CardItem item) {
        showItemDetails(item);
    }

    @Override
    public void onLikeClick(CardItem item) {
        toggleLike(item);
    }

    private void showItemDetails(CardItem item) {
        Toast.makeText(this, "Clicked: " + item.getTitle(), Toast.LENGTH_SHORT).show();
    }

    private void toggleLike(CardItem item) {
        int position = -1;
        for (int i = 0; i < cardItems.size(); i++) {
            if (cardItems.get(i).getId() == item.getId()) {
                position = i;
                break;
            }
        }

        if (position != -1) {
            CardItem currentItem = cardItems.get(position);
            CardItem updatedItem = new CardItem(
                    currentItem.getId(),
                    currentItem.getImageUrl(),
                    currentItem.getTitle(),
                    currentItem.getDescription(),
                    !currentItem.isLiked() ? currentItem.getLikes() + 1 : currentItem.getLikes() - 1,
                    !currentItem.isLiked()
            );

            cardItems.set(position, updatedItem);
            adapter.notifyItemChanged(position);

            String message = updatedItem.isLiked() ? "Liked" : "Unliked";
            Toast.makeText(this, message + ": " + item.getTitle(), Toast.LENGTH_SHORT).show();
        }
    }
}