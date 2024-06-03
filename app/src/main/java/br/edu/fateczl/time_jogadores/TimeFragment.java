package br.edu.fateczl.time_jogadores;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.edu.fateczl.time_jogadores.control.IController;
import br.edu.fateczl.time_jogadores.control.TimeController;
import br.edu.fateczl.time_jogadores.model.Time;
import br.edu.fateczl.time_jogadores.persistence.TimeDAO;


public class TimeFragment extends Fragment {
    private View view;

    private EditText inCodigoTime, inNomeTime, inCidadeTime;
    private Button btnInsert, btnUpdate, btnDelete, btnList, btnGet;
    private TextView tvListarTime;

    private IController<Time> timeControl;

    public TimeFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_time, container, false);
        timeControl = new TimeController(new TimeDAO(view.getContext()));

        inCodigoTime = view.findViewById(R.id.inCodTime);
        inNomeTime = view.findViewById(R.id.inNomeTime);
        inCidadeTime = view.findViewById(R.id.inCidadeTime);

        tvListarTime = view.findViewById(R.id.tvListarTime);
        tvListarTime.setMovementMethod(new ScrollingMovementMethod());

        btnInsert = view.findViewById(R.id.btnInsertTime);
        btnUpdate = view.findViewById(R.id.btnUpdateTime);
        btnDelete = view.findViewById(R.id.btnDeleteTime);
        btnList = view.findViewById(R.id.btnListTIme);
        btnGet = view.findViewById(R.id.btnBuscarTime);

        btnInsert.setOnClickListener(op -> acaoInserir());
        btnUpdate.setOnClickListener(op -> acaoAtualizar());
        btnDelete.setOnClickListener(op -> acaoExcluir());
        btnList.setOnClickListener(op -> acaoListar());
        btnGet.setOnClickListener(op -> acaoBuscar());
        return view;
    }

    private void acaoInserir() {
        try {
            checkEmptyFields();
            Time time = TimeFactoy();
            timeControl.inserir(time);
            Toast.makeText(view.getContext(), "Time Cadastrado com Sucesso!", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        cleanFields();
    }

    private void acaoAtualizar() {
        try {
            checkEmptyFields();
            Time time = TimeFactoy();
            timeControl.atualizar(time);
            Toast.makeText(view.getContext(), "Time Atualizado com Sucesso!", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        cleanFields();
    }

    private void acaoExcluir() {
        try {
            checkEmptyFields();
            Time time = TimeFactoy();
            timeControl.remover(time);
            Toast.makeText(view.getContext(), "Time Excluído com Sucesso!", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        cleanFields();
    }

    private void acaoBuscar() {
        try {
            if (inCodigoTime.getText().toString().isEmpty())
                throw new Exception("Codigo Para Busca Vazio!");
            Time time = TimeFactoy();
            time = timeControl.buscar(time);
            if (!time.getNomeTime().equals(" ")) {
                fillFields(time);
            }
            if (notFound()) {
                cleanFields();
                throw new Exception("Time Não Encontrado!");
            }
        } catch (Exception e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void acaoListar() {
        try {
            List<Time> times = timeControl.listar();
            StringBuilder buffer = new StringBuilder();
            for (Time t : times) {
                buffer.append(t.toString()).append("\n");
            }
            tvListarTime.setText(buffer.toString());
        } catch (Exception e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private Time TimeFactoy() throws Exception {
        Time t = new Time();
        t.setCodTime(Integer.parseInt(inCodigoTime.getText().toString()));
        t.setNomeTime(inNomeTime.getText().toString());
        t.setCidade(inCidadeTime.getText().toString());
        return t;
    }

    private void fillFields(Time t) {
        inCodigoTime.setText(String.valueOf(t.getCodTime()));
        inNomeTime.setText(t.getNomeTime());
        inCidadeTime.setText(t.getCidade());
    }

    private void cleanFields() {
        inCodigoTime.setText("");
        inNomeTime.setText("");
        inCidadeTime.setText("");
    }

    private boolean notFound() {
        if (inNomeTime.getText().toString().isEmpty() && inCidadeTime.getText().toString().isEmpty())
            return true;
        return false;
    }

    private void checkEmptyFields() throws Exception {
        if (inCidadeTime.getText().toString().isEmpty()) throw new Exception("Campos Vazios");
        if (inNomeTime.getText().toString().isEmpty()) throw new Exception("Campos Vazios");
        if (inCidadeTime.getText().toString().isEmpty()) throw new Exception("Campos Vazios");
    }
}