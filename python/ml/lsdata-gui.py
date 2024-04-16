import tkinter as tk
from tkinter import filedialog, messagebox
import pandas as pd
import matplotlib.pyplot as plt
from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg
import os

class DataPlotterApp:
    def __init__(self, master):
        self.master = master
        self.master.title('Data Plotter')

        self.current_file_index = 0
        self.csv_files = []
        self.directory = None  # Initialize directory variable
        self.load_files()

        # Create navigation buttons
        btn_prev = tk.Button(master, text='Previous', command=self.show_previous)
        btn_prev.pack(side=tk.LEFT, padx=(20, 10), pady=20)

        btn_next = tk.Button(master, text='Next', command=self.show_next)
        btn_next.pack(side=tk.RIGHT, padx=(10, 20), pady=20)

        # Canvas for matplotlib graph
        self.fig, self.ax = plt.subplots(figsize=(8, 6))
        self.canvas = FigureCanvasTkAgg(self.fig, master=self.master)
        self.canvas_widget = self.canvas.get_tk_widget()
        self.canvas_widget.pack(fill=tk.BOTH, expand=True)

        if self.csv_files:
            self.update_plot()

    def load_files(self):
        self.directory = filedialog.askdirectory(title='Select Data Folder')
        if self.directory:
            self.csv_files = [f for f in os.listdir(self.directory) if f.endswith('.csv')]
            if not self.csv_files:
                messagebox.showerror("Error", "No CSV files found in the directory.")
                self.master.quit()

    def update_plot(self):
        if not self.csv_files:
            return
        file_path = os.path.join(self.directory, self.csv_files[self.current_file_index])
        data = pd.read_csv(file_path)
        data['grading_level'] = data['grading_level'].astype(str)

        self.ax.clear()
        self.ax.plot(data['grading_level'], data['student_score'], 'bo-', label='Student Score')
        self.ax.plot(data['grading_level'], data['expected_score'], 'ro-', label='Expected Score')
        self.ax.set_xlabel('Grading Level')
        self.ax.set_ylabel('Score')
        self.ax.set_title('Student Score vs Expected Score')
        self.ax.legend()
        self.ax.set_xticklabels(data['grading_level'], rotation=45)
        self.fig.tight_layout()

        self.canvas.draw()

    def show_next(self):
        if self.current_file_index < len(self.csv_files) - 1:
            self.current_file_index += 1
            self.update_plot()

    def show_previous(self):
        if self.current_file_index > 0:
            self.current_file_index -= 1
            self.update_plot()

if __name__ == "__main__":
    root = tk.Tk()
    app = DataPlotterApp(root)
    root.mainloop()
