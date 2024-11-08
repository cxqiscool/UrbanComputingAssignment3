import pandas as pd
from sqlalchemy import create_engine

# Configure database information
db_user = "myuser"
db_password = "mypassword"
db_host = "localhost"
db_port = "5432"
db_name = "sonitus_data"

# Creating a database connection URL
database_url = f"postgresql://{db_user}:{db_password}@{db_host}:{db_port}/{db_name}"

# Creating a Connection Engine with SQLAlchemy
engine = create_engine(database_url)

# Read CSV file to DataFrame
csv_file_path = "combined_senseboxes.csv"  # file path
df = pd.read_csv(csv_file_path)

# Modify the DataFrame's column names to match PostgreSQL's column name formatting
df.columns = [col.lower().replace(" ", "_") for col in df.columns]

# Importing Data to a Table in a PostgreSQL Databas
table_name = "senseboxes"
df.to_sql(table_name, engine, if_exists="append", index=False)

print("The data has been successfully imported into the PostgreSQL database!")