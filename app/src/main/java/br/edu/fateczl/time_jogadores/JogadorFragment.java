package br.edu.fateczl.time_jogadores;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

import br.edu.fateczl.time_jogadores.control.IController;
import br.edu.fateczl.time_jogadores.control.JogadorController;
import br.edu.fateczl.time_jogadores.control.TimeController;
import br.edu.fateczl.time_jogadores.model.Jogador;
import br.edu.fateczl.time_jogadores.model.Time;
import br.edu.fateczl.time_jogadores.persistence.JogadorDAO;
import br.edu.fateczl.time_jogadores.persistence.TimeDAO;


/**
 * @noinspection ALL
 */

public class JogadorFragment extends Fragment {
    private View view;

    private EditText inCodigoJog, inNomeJog, inDataNasc, inAltura, inPeso;
    private Button btnInsert, btnUpdate, btnDelete, btnList, btnGet;
    private TextView tvListarJog;
    private Spinner spTimesJog;

    private IController<Jogador> jControl;
    private IController<Time> tControl;
    private List<Time> times;

    public JogadorFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_jogador, container, false);

        jControl = new JogadorController(new JogadorDAO(view.getContext()));
        tControl = new TimeController(new TimeDAO(view.getContext()));

        inCodigoJog = view.findViewById(R.id.inCodJog);
        inNomeJog = view.findViewById(R.id.inNomeJog);
        inDataNasc = view.findViewById(R.id.inDataNascJog);
        inAltura = view.findViewById(R.id.inAlturaJog);
        inPeso = view.findViewById(R.id.inPesoJog);

        tvListarJog = view.findViewById(R.id.outJogadores);
        tvListarJog.setMovementMethod(new ScrollingMovementMethod());

        btnInsert = view.findViewById(R.id.btnInsertJog);
        btnUpdate = view.findViewById(R.id.btnUpdateJog);
        btnDelete = view.findViewById(R.id.btnDeleteJog);
        btnGet = view.findViewById(R.id.btnBuscarJog);
        btnList = view.findViewById(R.id.btnListJog);

        btnInsert.setOnClickListener(op -> {
            try {
                acaoInserir();
            } catch (Exception e) {
                Log.e("SQL", e.toString());
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        btnUpdate.setOnClickListener(op -> acaoAtualizar());
        btnDelete.setOnClickListener(op -> acaoExcluir());
        btnGet.setOnClickListener(op -> acaoBuscar());
        btnList.setOnClickListener(op -> acaoListar());

        spTimesJog = view.findViewById(R.id.spTImesJog);
        fillSpinner();
        return view;
    }

    private void acaoInserir() {
        int pos = spTimesJog.getSelectedItemPosition();
        if (pos > 0) {
            try {
                checkEmptyFields();
                Jogador jogador = JogadorFactory();
                jControl.inserir(jogador);
                Toast.makeText(view.getContext(), "Jogador cadastrado com êxito", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
            cleanFields();
        } else {
            Toast.makeText(view.getContext(), "Selecione um Time", Toast.LENGTH_LONG).show();
        }
    }

    private void acaoAtualizar() {
        int pos = spTimesJog.getSelectedItemPosition();
        if (pos > 0) {
            try {
                checkEmptyFields();
                Jogador jogador = JogadorFactory();
                jControl.atualizar(jogador);
                Toast.makeText(view.getContext(), "Jogador Atualizado com Êxito", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
            cleanFields();
        } else {
            Toast.makeText(view.getContext(), "Selecione um Time", Toast.LENGTH_LONG).show();
        }
    }

    private void acaoExcluir() {
        int pos = spTimesJog.getSelectedItemPosition();
        if (pos > 0) {
            try {
                checkEmptyFields();
                Jogador jogador = JogadorFactory();
                jControl.remover(jogador);
                Toast.makeText(view.getContext(), "Jogador Excluído com Êxito", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
            cleanFields();
        } else {
            Toast.makeText(view.getContext(), "Selecione um Time", Toast.LENGTH_LONG).show();
        }
    }

    private void acaoBuscar() {
        try {
            if (inCodigoJog.getText().toString().isEmpty())
                throw new Exception("Codigo Para Busca Vazio!");
            Jogador jogador = JogadorFactory();
            times = tControl.listar();
            jogador = jControl.buscar(jogador);
            if (jogador.getNomeJogador() != null) {
                if (notFound()) {
                    Toast.makeText(view.getContext(), "Jogador Não Encontrado", Toast.LENGTH_LONG).show();
                    cleanFields();
                }
                fillFields(jogador);
            }
        } catch (Exception e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void acaoListar() {
        try {
            List<Jogador> jogadores = jControl.listar();
            StringBuilder buffer = new StringBuilder();
            for (Jogador j : jogadores) {
                buffer.append(j.toString()).append("\n");
            }
            tvListarJog.setText(buffer.toString());
        } catch (Exception e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private Jogador JogadorFactory() {
        Jogador j = new Jogador();
        j.setIdJogador(Integer.parseInt(inCodigoJog.getText().toString()));
        j.setNomeJogador(inNomeJog.getText().toString());
        j.setDataNasc(inDataNasc.getText().toString());

        if (inAltura.getText().toString().isEmpty()) j.setAltura(0);
        else j.setAltura(Float.parseFloat(inAltura.getText().toString()));
        if (inPeso.getText().toString().isEmpty()) j.setPeso(0);
        else j.setPeso(Float.parseFloat(inPeso.getText().toString()));

        j.setTime((Time) spTimesJog.getSelectedItem());
        return j;
    }

    private void fillSpinner() {
        Time t0 = new Time();
        t0.setCodTime(0);
        t0.setNomeTime("Selecione um Time");
        t0.setCidade("");
        try {
            times = tControl.listar();
            times.add(0, t0);
            ArrayAdapter ad = new ArrayAdapter(view.getContext(), android.R.layout.simple_spinner_item, times);
            ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spTimesJog.setAdapter(ad);
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void fillFields(Jogador jogador) {
        inCodigoJog.setText(String.valueOf(jogador.getIdJogador()));
        inNomeJog.setText(jogador.getNomeJogador());
        inDataNasc.setText(jogador.getDataNasc());
        inAltura.setText(String.valueOf(jogador.getAltura()));
        inPeso.setText(String.valueOf(jogador.getPeso()));

        int pos = 1;
        for (Time t : times) {
            if (t.getCodTime() == jogador.getTime().getCodTime()) {
                spTimesJog.setSelection(pos);
            } else {
                pos += 1;
            }
        }
        if (pos > times.size()) {
            spTimesJog.setSelection(0);
        }
    }

    private void checkEmptyFields() throws Exception {
        if (inCodigoJog.getText().toString().isEmpty()) throw new Exception("Campos Vazios");
        if (inNomeJog.getText().toString().isEmpty()) throw new Exception("Campos Vazios");
        if (inDataNasc.getText().toString().isEmpty()) throw new Exception("Campos Vazios");
        if (inAltura.getText().toString().isEmpty()) throw new Exception("Campos Vazios");
        if (inPeso.getText().toString().isEmpty()) throw new Exception("Campos Vazios");
    }

    private boolean notFound() {
        if (inNomeJog.getText().toString().isEmpty() || inDataNasc.getText().toString().isEmpty() || inAltura.getText().toString().equals(0) || inPeso.getText().toString().equals(0))
            return true;
        return false;
    }

    private void cleanFields() {
        inCodigoJog.setText("");
        inNomeJog.setText("");
        inDataNasc.setText("");
        inAltura.setText("");
        inPeso.setText("");
        spTimesJog.setSelection(0);
    }
}