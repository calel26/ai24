import matplotlib.pyplot as plt
import caloriecalc

cats = caloriecalc.get_categories()
foods = caloriecalc.get_foods()

# x and y axis data
x = list(dict.fromkeys(cats))
x.pop(0)
y = [0 for _ in range(len(x))]
foods_in_cat = [0 for _ in range(len(x))]

for i in range(1, len(foods)):
    cat = cats[i]
    cals = caloriecalc.get_food(foods[i])
    cat_i = x.index(cat)
    y[cat_i] += cals
    foods_in_cat[cat_i] += 1

for i in range(len(y)):
    y[i] /= foods_in_cat[i]

# figure out the height of the bars
y_pos = range(len(x))

# create a bar graph
plt.bar(y_pos, y)

# Rotation of the bars names beneath the graph
plt.xticks(y_pos, x, rotation=90)

# x axis label
plt.xlabel('Category', fontsize=15)

# y axis label
plt.ylabel('Average Calories', fontsize=15)

# make sure everything appears on the screen
plt.tight_layout()
# show the bar graph
plt.show()
