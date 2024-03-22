import pandas as pd
import matplotlib.pyplot as plt

df = pd.read_csv("./countries.csv")
print(df.shape)
# Assuming 'df' is your DataFrame and it has columns ['Country', 'Year', 'Population']
# If your DataFrame has different column names, adjust the column references accordingly.

# Step 1: Filter the DataFrame for the countries of interest
us = df[df.country == "United States"]
jp = df[df.country == "Japan"]
us['pct'] = us['population'].pct_change() * 100
jp['pct'] = jp['population'].pct_change() * 100

plt.figure(figsize=(10, 6))  # Set the figure size for better readability

plt.xlabel('Year')  # X-axis label
plt.ylabel('Population')  # Y-axis label

plt.plot(us['year'], us['pct'], label=us['country'])
plt.plot(jp['year'], jp['pct'], label=jp['country'])

plt.title('Population Increase (%)')  # Chart title
# plt.legend()  # Show legend to identify the lines
plt.show()  # Display the plot
