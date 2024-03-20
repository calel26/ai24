import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

df = pd.read_csv("./countries.csv")
print(df.shape)
# Assuming 'df' is your DataFrame and it has columns ['Country', 'Year', 'Population']
# If your DataFrame has different column names, adjust the column references accordingly.

# Step 1: Filter the DataFrame for the countries of interest
us = df[df.country == "United States"]
jp = df[df.country == "Japan"]

# calculate increase

plt.figure(figsize=(10, 6))  # Set the figure size for better readability
plt.plot(us['year'], us['population'], label=us['country'])
plt.plot(jp['year'], jp['population'], label=jp['country'])

plt.xlabel('Year')  # X-axis label
plt.ylabel('Population')  # Y-axis label
plt.title('Population Growth')  # Chart title
# plt.legend()  # Show legend to identify the lines
plt.show()  # Display the plot
'''   
# Plotting
plt.figure(figsize=(10, 6))  # Set the figure size for better readability

for country in ['China', 'United States', 'Japan']:
    country_df = filtered_df[filtered_df['country'] == country]
    plt.plot(country_df['year'], country_df['population'], label=country)

plt.xlabel('Year')  # X-axis label
plt.ylabel('Population')  # Y-axis label
plt.title('Population Growth')  # Chart title
plt.legend()  # Show legend to identify the lines
plt.show()  # Display the plot
'''
