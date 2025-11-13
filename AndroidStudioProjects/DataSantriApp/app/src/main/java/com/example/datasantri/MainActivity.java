package com.example.datasantri;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlertDialog;
import android.widget.*;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listViewSantri;
    Button btnAddSantri;
    ArrayList<Santri> dataSantri;
    ArrayAdapter<Santri> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewSantri = findViewById(R.id.listViewSantri);
        btnAddSantri = findViewById(R.id.btnAddSantri);

        dataSantri = new ArrayList<>();
        dataSantri.add(new Santri("Hasbi", "7A", "Cirebon", "08123456789"));
        dataSantri.add(new Santri("Laila", "8B", "Bandung", "082233445566"));

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataSantri);
        listViewSantri.setAdapter(adapter);

        btnAddSantri.setOnClickListener(v -> showAddDialog());

        listViewSantri.setOnItemClickListener((parent, view, position, id) -> showEditDialog(position));

        listViewSantri.setOnItemLongClickListener((parent, view, position, id) -> {
            new AlertDialog.Builder(this)
                    .setTitle("Hapus Data")
                    .setMessage("Yakin mau hapus data ini?")
                    .setPositiveButton("Ya", (dialog, which) -> {
                        dataSantri.remove(position);
                        adapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("Batal", null)
                    .show();
            return true;
        });
    }

    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tambah Santri Baru");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(30, 20, 30, 10);

        EditText inputNama = new EditText(this);
        inputNama.setHint("Nama Santri");
        layout.addView(inputNama);

        EditText inputKelas = new EditText(this);
        inputKelas.setHint("Kelas");
        layout.addView(inputKelas);

        EditText inputAsal = new EditText(this);
        inputAsal.setHint("Asal Daerah");
        layout.addView(inputAsal);

        EditText inputNoHp = new EditText(this);
        inputNoHp.setHint("Nomor HP");
        inputNoHp.setInputType(android.text.InputType.TYPE_CLASS_PHONE);
        layout.addView(inputNoHp);

        builder.setView(layout);

        builder.setPositiveButton("Simpan", (dialog, which) -> {
            String nama = inputNama.getText().toString();
            String kelas = inputKelas.getText().toString();
            String asal = inputAsal.getText().toString();
            String noHp = inputNoHp.getText().toString();

            if (!nama.isEmpty()) {
                dataSantri.add(new Santri(nama, kelas, asal, noHp));
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "Nama tidak boleh kosong!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Batal", null);
        builder.show();
    }

    private void showEditDialog(int position) {
        Santri santri = dataSantri.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Data Santri");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(30, 20, 30, 10);

        EditText inputNama = new EditText(this);
        inputNama.setText(santri.getNama());
        layout.addView(inputNama);

        EditText inputKelas = new EditText(this);
        inputKelas.setText(santri.getKelas());
        layout.addView(inputKelas);

        EditText inputAsal = new EditText(this);
        inputAsal.setText(santri.getAsal());
        layout.addView(inputAsal);

        EditText inputNoHp = new EditText(this);
        inputNoHp.setText(santri.getNoHp());
        layout.addView(inputNoHp);

        builder.setView(layout);

        builder.setPositiveButton("Simpan", (dialog, which) -> {
            Santri santriBaru = new Santri(
                    inputNama.getText().toString(),
                    inputKelas.getText().toString(),
                    inputAsal.getText().toString(),
                    inputNoHp.getText().toString()
            );
            dataSantri.set(position, santriBaru);
            adapter.notifyDataSetChanged();
        });

        builder.setNegativeButton("Batal", null);
        builder.show();
    }
}
