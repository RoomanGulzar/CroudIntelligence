package com.google.croudintelligence.Models;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class staticClass {
    @Deprecated
    public static User user;


  /*  public static void setStars(FragmentProfileBinding binding, float rating) {
        // Set appropriate star icons based on number of filled stars and half-star flag
        ImageView star1 = binding.star1;
        ImageView star2 = binding.star2;
        ImageView star3 = binding.star3;
        ImageView star4 = binding.star4;
        ImageView star5 = binding.star5;

        int numFilledStars = (int) rating;
        boolean hasHalfStar = (rating - numFilledStars) >= 0.5f;
        star1.setImageResource(numFilledStars >= 1 ? R.drawable.baseline_star_24 : R.drawable.baseline_star_empty_24);
        star2.setImageResource(numFilledStars >= 2 ? R.drawable.baseline_star_24 : R.drawable.baseline_star_empty_24);
        star3.setImageResource(numFilledStars >= 3 ? R.drawable.baseline_star_24 : R.drawable.baseline_star_empty_24);
        star4.setImageResource(numFilledStars >= 4 ? R.drawable.baseline_star_24 : R.drawable.baseline_star_empty_24);
        star5.setImageResource(numFilledStars >= 5 ? R.drawable.baseline_star_24 : R.drawable.baseline_star_empty_24);

        if (hasHalfStar) {
            if (numFilledStars >= 5) {
                star5.setImageResource(R.drawable.baseline_star_half_24);
            } else if (numFilledStars >= 4) {
                star4.setImageResource(R.drawable.baseline_star_half_24);
            } else if (numFilledStars >= 3) {
                star3.setImageResource(R.drawable.baseline_star_half_24);
            } else if (numFilledStars >= 2) {
                star2.setImageResource(R.drawable.baseline_star_half_24);
            } else if (numFilledStars >= 1) {
                star1.setImageResource(R.drawable.baseline_star_half_24);
            }
        } else {
            // Clear any previous half-star icons
            if (numFilledStars < 5) {
                star5.setImageResource(R.drawable.baseline_star_empty_24);
            }
            if (numFilledStars < 4) {
                star4.setImageResource(R.drawable.baseline_star_empty_24);
            }
            if (numFilledStars < 3) {
                star3.setImageResource(R.drawable.baseline_star_empty_24);
            }
            if (numFilledStars < 2) {
                star2.setImageResource(R.drawable.baseline_star_empty_24);
            }
            if (numFilledStars < 1) {
                star1.setImageResource(R.drawable.baseline_star_empty_24);
            }
        }

    }
*/


    public static ArrayList<Topic> userTopics;


    public static void updateBoxes(Context context, ArrayList<String> data, LinearLayout parentLayout, ArrayList<CheckBox> checkBoxes, ArrayList<String> tags) {
        // LinearLayout parentLayout = findViewById(R.id.container_checkBox);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1
        );
        layoutParams.weight = 1;
        for (int i = 0; i < data.size(); i += 2) {
            LinearLayout rowLayout = new LinearLayout(context);
            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
            CheckBox checkBox1 = new CheckBox(context);
            checkBox1.setText(data.get(i));
            checkBox1.setTag(tags.get(i));
            checkBox1.setLayoutParams(layoutParams);
            checkBoxes.add(checkBox1);
            rowLayout.addView(checkBox1);
            if (i + 1 < data.size()) {
                CheckBox checkBox2 = new CheckBox(context);
                checkBox2.setText(data.get(i + 1));
                checkBox2.setLayoutParams(layoutParams);
                checkBox2.setTag(tags.get(i + 1));
                rowLayout.addView(checkBox2);
                checkBoxes.add(checkBox2);
            }
            parentLayout.addView(rowLayout);
        }
    }

    static int n = 0;

    public static void setListenerLimitTo5(ArrayList<CheckBox> checkBoxes) {

        // Toast.makeText(context, checkBoxes.size()+"", Toast.LENGTH_SHORT).show();
        n = 0;
        for (int i = 0; i < checkBoxes.size(); i++) {
            int finalI = i;
            checkBoxes.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        //   Toast.makeText(context, "checked", Toast.LENGTH_SHORT).show();
                        n++;
                        if (n >= 5) {
                            for (int j = 0; j < checkBoxes.size(); j++) {
                                if (!checkBoxes.get(j).isChecked()) {
                                    checkBoxes.get(j).setEnabled(false);
                                }
                            }
                        }
                    } else {
                        n--;
                        if (n < 5) {
                            for (int j = 0; j < checkBoxes.size(); j++) {
                                if (!checkBoxes.get(j).isChecked()) {
                                    checkBoxes.get(j).setEnabled(true);
                                }
                            }
                        }
                    }

                }
            });
        }
    }


}
