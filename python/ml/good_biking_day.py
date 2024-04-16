import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.tree import DecisionTreeClassifier
from sklearn.metrics import accuracy_score, f1_score
from sklearn.tree import export_graphviz
from graphviz import Source
import numpy as np

# Load data
df = pd.read_csv("london_merged.csv")

# Preprocess data (handle missing values, encode categorical features)
# ... (your data cleaning steps here)
# possible steps below
# Convert 'age' from days to years
# df['age'] = df['age'] / 365.25
# dropping data
# Option 1: Drop rows with any missing values
# (at least one missing value)
df = df.dropna()

# Option 2: Drop rows with missing values in specific columns
# specific_cols = ["weight", "smoke"]  # Replace with your columns
# df = df.dropna(subset=specific_cols)

# Handle missing numerical data (consider filling with mean/median)
# Example: Imputing missing values in "BloodPressure" with mean
# df["ap_hi"] = df["ap_hi"].fillna(df["ap_hi"].mean())

# Handle missing categorical data (consider filling with mode )
# Example: Filling missing values in "cholesterol" with mode
# (most frequent value)
# df["cholesterol"] = df["cholesterol"].fillna(df["cholesterol"].mode()[0])

# Encode categorical features (e.g., one-hot encoding)
# if some categories used letters like "SmokingStatus" (Yes/No)
# or "Sex" (Male/Female)

# categorical_features = ["SmokingStatus", "Sex"]
# df = pd.get_dummies(df, columns=categorical_features, drop_first=True)

mean_bikes = df["cnt"].mean() * 0.6
above = df["cnt"] > mean_bikes
is_day_off = (df["is_holiday"] == 1) | (df["is_weekend"] == 1)
df["is_day_off"] = is_day_off

# Define features and target variables
# t2 = feels like
feature_names = ["is_day_off", "wind_speed", "hum", "t2", "weather_code", "season"]
X = df[feature_names]  # the X axis is equal to columns in featured_names
y = above

# Split data
X_train, X_test, y_train, y_test = train_test_split(
    X, y, test_size=0.2, random_state=42)

# Build the decision tree
model = DecisionTreeClassifier(max_depth=3)  # Adjust max_depth as needed
model.fit(X_train, y_train)

# Make predictions
y_pred = model.predict(X_test)

# Evaluate model (optional)
accuracy = accuracy_score(y_test, y_pred)
f1 = f1_score(y_test, y_pred)
print("Accuracy:", accuracy)
print("F1:", f1)

# Visualize decision tree
dot_data = export_graphviz(model, out_file=None,
                           feature_names=X_train.columns,
                           class_names=["not many bikes", "big biking day"],
                           filled=True, rounded=True,
                           special_characters=True,
                           proportion=True,  # Shows class proportions instead of counts
                           impurity=True,  # Shows the impurity at each node
                           node_ids=True,  # Shows node IDs
                           )

graph = Source(dot_data)
graph.render("biking_tree")


def query_decision_tree(model, feature_names):
    # This function navigates the decision tree model asking the user for input.
    # `model` is your trained DecisionTreeClassifier.
    # `feature_names` is a list of the names of the features used by the model.

    # The decision path is determined by the model's tree structure.
    tree = model.tree_

    def ask_question(node=0):
        # If we have a leaf node, show the prediction
        if tree.children_left[node] == tree.children_right[node]:  # leaf
            pred = np.argmax(tree.value[node])
            if pred == 0:
                print("it's NOT A GOOD BIKING DAY :(")
            else:
                print("it's a great biking day!")
            return

        # Otherwise, ask the next question
        feature = feature_names[tree.feature[node]]
        threshold = tree.threshold[node]
        answer = input(f"Is the {feature} <= {threshold}? (yes/no): ")

        # Navigate to the next node based on the answer
        if answer.lower() == "yes":
            ask_question(tree.children_left[node])
        else:
            ask_question(tree.children_right[node])

    ask_question()


# Example usage:
# Assuming your model is trained and `X_train.columns` holds your feature names
query_decision_tree(model, X_train.columns)
