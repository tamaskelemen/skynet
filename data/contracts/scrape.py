import tabula

import os

datafolder = os.path.join(os.getcwd(), 'data')
outfolder = os.path.join(os.getcwd(), 'csv_gen')
for filename in os.listdir(datafolder):
    dfl = tabula.read_pdf(os.path.join(datafolder, filename), pages='all', lattice=True)
    for df in dfl:
        df.replace(to_replace=[r"\\t|\\n|\\r", "\t|\n|\r"], value=["",""], regex=True, inplace=True)
        base = os.path.splitext(filename)[0]
        df.to_csv(os.path.join(outfolder, base + '.csv'), mode='a', header=False)
