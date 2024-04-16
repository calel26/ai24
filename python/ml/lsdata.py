import pandas as pd
import matplotlib.pyplot as plt
import os


def plot_data(file_path):
    data = pd.read_csv(file_path)
    data['grading_level'] = data['grading_level'].astype(str)

    plt.figure(figsize=(8, 6))  # Adjust figure size as needed
    plt.plot(data['grading_level'], data['student_score'],
             'bo-', label='Student Score')
    plt.plot(data['grading_level'], data['expected_score'],
             'ro-',  label='Expected Score')

    # labels and such
    plt.xlabel('Grading Level')
    plt.ylabel('Score')
    plt.title('Student Score vs Expected Score by Grading Level')
    plt.legend()
    plt.xticks(rotation=45)  # Rotate x-axis labels for readability
    plt.tight_layout()
    plt.show()


def main():
    directory = './lscsv'
    csv_files = [f for f in os.listdir(directory) if f.endswith('.csv')]

    if not csv_files:
        print("No CSV files found in the directory.")
        return

    current_file_index = 0
    while True:
        print(f"Displaying {csv_files[current_file_index]}")
        plot_data(os.path.join(directory, csv_files[current_file_index]))

        command = input(
            "Enter 'n' to go to the next file, 'p' for previous, or 'q' to quit: ").strip().lower()
        if command == 'n':
            if current_file_index < len(csv_files) - 1:
                current_file_index += 1
            else:
                print("This is the last file.")
        elif command == 'p':
            if current_file_index > 0:
                current_file_index -= 1
            else:
                print("This is the first file.")
        elif command == 'q':
            break
        else:
            print("Invalid command. Please enter 'n', 'p', or 'q'.")


if __name__ == "__main__":
    main()
