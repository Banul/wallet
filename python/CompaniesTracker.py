import sqlite3

def save_tracked(tickers):
    conn = sqlite3.connect('tracked_companies.db')
    cursor = conn.cursor()
    cursor.execute('''
                   CREATE TABLE IF NOT EXISTS companies
                   (
                       id INTEGER PRIMARY KEY AUTOINCREMENT,
                       ticker TEXT UNIQUE
                   )
                   ''')

    cursor.executemany(
        'INSERT OR IGNORE INTO companies (ticker) VALUES (?)',
        [(ticker,) for ticker in tickers]
    )
    conn.commit()
    conn.close()
