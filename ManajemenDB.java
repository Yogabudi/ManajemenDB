package septiandp.kamusistilahmusik;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Random;

public class ManajemenDB extends SQLiteOpenHelper {

    public final static String NAMADB = "kamusmusik.db";
    public final static String TABEL_MUSIK = "TabelMusik";
    public final static String TABEL_SARAN_BACAAN = "TabelSaranBacaan";

    private Context cont;
    private SQLiteDatabase db;
    private static ManajemenDB objek;

    private ManajemenDB(Context cont) {
        super(cont, NAMADB, null, 1);
        this.cont = cont;
        this.objek = null;
    }

    public static ManajemenDB dapatkanObjek(Context cont) {
        if(objek == null) {
            objek = new ManajemenDB(cont);
            objek.buka();
        }

        return objek;
    }

    public void buka() {
        try {
            db = getWritableDatabase();
            System.out.println("DIEKSEKUSI !!!!");
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public void tutup() {
        if(db.isOpen()) {
            db.close();
        }
    }

    public void input(String namaTabel, StrukturTabel data) {
        Cursor kur = db.rawQuery("select * from " + namaTabel, null);

        int jumlahField = kur.getColumnCount();
        ContentValues val = new ContentValues();

        for(int i = 1; i < jumlahField; i++) {
            val.put(kur.getColumnName(i), data.dapatkanData(i));
        }

        db.insert(namaTabel, null, val);
    }

    public ArrayList<StrukturTabel> dapatkanSemuaData(String namaTabel) {
        ArrayList<StrukturTabel> data = new ArrayList<>();

        Cursor kur = db.rawQuery("select * from " + namaTabel, null);
        kur.moveToFirst();

        int jumlahField = kur.getColumnCount();

        while(!kur.isAfterLast()) {
            String[] sebarisData = new String[jumlahField];

            for(int i = 0; i < jumlahField; i++) {
                sebarisData[i] = kur.getString(i);
            }

            data.add(new StrukturTabel(sebarisData));

            kur.moveToNext();
        }

        return data;
    }

    public ArrayList<StrukturTabel> dapatkanSemuaDataRandom(String namaTabel) {
        ArrayList<StrukturTabel> data = new ArrayList<>();

        Cursor kur = db.rawQuery("select * from " + namaTabel, null);
        kur.moveToFirst();

        int jumlahField = kur.getColumnCount();
        int jumlahData = kur.getCount();
        int[] indexRandom = angkaRandom(jumlahData);
        ArrayList<StrukturTabel> dataRandom = new ArrayList<>();

        while(!kur.isAfterLast()) {
            String[] sebarisData = new String[jumlahField];

            for(int i = 0; i < jumlahField; i++) {
                sebarisData[i] = kur.getString(i);
            }

            data.add(new StrukturTabel(sebarisData));

            kur.moveToNext();
        }

        for(int i = 0; i < data.size(); i++) {
            dataRandom.add(data.get(indexRandom[i]));
        }

        System.out.println("JUMLAH DATA : " + dataRandom.size());

        return dataRandom;
    }

    public ArrayList<StrukturTabel> dapatkanSemuaDataTerfilter(String namaTabel,
                                                               String field,
                                                               String value) {
        ArrayList<StrukturTabel> data = new ArrayList<>();

        Cursor kur = db.rawQuery("select * from " + namaTabel +
                " where " + field + " = " + "'" + value + "'", null);
        kur.moveToFirst();

        int jumlahField = kur.getColumnCount();

        while(!kur.isAfterLast()) {
            String[] sebarisData = new String[jumlahField];

            for(int i = 0; i < jumlahField; i++) {
                sebarisData[i] = kur.getString(i);
            }

            data.add(new StrukturTabel(sebarisData));

            kur.moveToNext();
        }

        return data;
    }

    public ArrayList<StrukturTabel> dapatkanSemuaDataRandomTerfilter(String namaTabel,
                                                                     String field,
                                                                     String value) {
        ArrayList<StrukturTabel> data = new ArrayList<>();

        Cursor kur = db.rawQuery("select * from " + namaTabel +
                " where " + field + " = " + "'" + value + "'", null);
        kur.moveToFirst();

        int jumlahField = kur.getColumnCount();
        int jumlahData = kur.getCount();
        int[] indexRandom = angkaRandom(jumlahData);
        ArrayList<StrukturTabel> dataRandom = new ArrayList<>();

        while(!kur.isAfterLast()) {
            String[] sebarisData = new String[jumlahField];

            for(int i = 0; i < jumlahField; i++) {
                sebarisData[i] = kur.getString(i);
            }

            data.add(new StrukturTabel(sebarisData));

            kur.moveToNext();
        }

        for(int i = 0; i < data.size(); i++) {
            dataRandom.add(data.get(indexRandom[i]));
            System.out.println("SOAL RANDOM : " + dataRandom.get(i).dapatkanData(1));
        }

        System.out.println("JUMLAH DATA : " + dataRandom.size());

        return dataRandom;
    }

    public int[] angkaRandom(int banyaknya) {
        int[] hasil = new int[banyaknya];
        Random rand = new Random();

        for(int i = 0; i < hasil.length; i++) {
            hasil[i] = -1;
        }

        hasil[0] = rand.nextInt(banyaknya);

        for(int i = 1; i < hasil.length; i++) {
            boolean valid = true;
            int angkaRandom = rand.nextInt(banyaknya);

            for(int r = 0; hasil[r] != -1; r++) {
                if(hasil[r] == angkaRandom) {
                    valid = false;
                }
            }

            if(valid) {
                hasil[i] = angkaRandom;
            }
            else {
                i--;
            }
        }

        return hasil;
    }

    public StrukturTabel dapatkanData(String namaTabel, String field, String value) {
        Cursor kur = db.rawQuery("select * from " + namaTabel +
                " where " + field + " = '" + value + "'",null);
        kur.moveToFirst();

        int jumlahField = kur.getColumnCount();
        String[] sebarisData = new String[jumlahField];

        for(int i = 0; i < jumlahField; i++) {
            sebarisData[i] = kur.getString(i);
        }

        return new StrukturTabel(sebarisData);
    }

    public void update(String namaTabel, String fieldKriteria, String valKriteria, String... field) {
        Cursor kur = db.rawQuery("select * from " + namaTabel, null);

        int jumlahField = kur.getColumnCount();
        ContentValues val = new ContentValues();

        for(int i = 1; i < jumlahField; i++) {
            val.put(kur.getColumnName(i), field[i]);
        }

        db.update(namaTabel, val, fieldKriteria + " = " + "'" + valKriteria + "'", null);
    }

    public void hapus(String namaTabel, String field, String val) {
        db.delete(namaTabel, field + " = " + val, null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(cont.getResources().getString(R.string.TabelMusik));
        sqLiteDatabase.execSQL(cont.getResources().getString(R.string.TabelSaranBacaan));

        ContentValues val = new ContentValues();
        val.put("kata", "A");
        val.put("deskripsi", "Huruf pertama di abjad");
        val.put("favorit", "false");
        sqLiteDatabase.insert(ManajemenDB.TABEL_MUSIK, null, val);

        val = new ContentValues();
        val.put("kata", "B");
        val.put("deskripsi", "Huruf kedua di abjad");
        val.put("favorit", "false");
        sqLiteDatabase.insert(ManajemenDB.TABEL_MUSIK, null, val);

        val = new ContentValues();
        val.put("kata", "C");
        val.put("deskripsi", "Huruf ketiga di abjad");
        val.put("favorit", "false");
        sqLiteDatabase.insert(ManajemenDB.TABEL_MUSIK, null, val);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(cont.getResources().getString(R.string.HapusSemuaTabel));
        onCreate(sqLiteDatabase);
    }

    public class StrukturTabel {
        private String[] data;

        public StrukturTabel(String[] data) {
            this.data = new String[data.length];

            for(int i = 0; i < data.length; i++) {
                this.data[i] = data[i];
            }
        }

        public void setData(int indexField, String data) {
            this.data[indexField] = data;
        }

        public String dapatkanData(int indexField) {
            return this.data[indexField];
        }

        public int dapatkanJumlahField() {
            return this.data.length;
        }
    }
}
