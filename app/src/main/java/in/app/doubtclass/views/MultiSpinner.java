package in.app.doubtclass.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import in.app.doubtclass.R;
import in.app.doubtclass.adapters.MultiChoiceAdapter;
import in.app.doubtclass.model.GradeBean;

public class MultiSpinner extends Spinner implements DialogInterface.OnMultiChoiceClickListener, DialogInterface.OnCancelListener {

    private List<String> items;
    private List<GradeBean> mGradeBeanList = new ArrayList<GradeBean>();
    private boolean[] selected;
    private MultiSpinnerListener listener;
    private String mGradeIds="";
    private String mGrades="";
    private MultiChoiceAdapter multiChoiceAdapter;
    private AlertDialog alertDialog;
    public MultiSpinner(Context context) {
        super(context);
    }

    public MultiSpinner(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
    }

    public MultiSpinner(Context arg0, AttributeSet arg1, int arg2) {
        super(arg0, arg1, arg2);
    }

    @Override
    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        if (isChecked)
            selected[which] = true;
        else
            selected[which] = false;
    }

    @Override
    public boolean performClick() {
        multiChoiceAdapter = new MultiChoiceAdapter(mGradeBeanList,selected);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setAdapter(multiChoiceAdapter,null);
        builder.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog = builder.create();
        ListView listView = alertDialog.getListView();
        listView.setAdapter(multiChoiceAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected[position] = !selected[position];
                multiChoiceAdapter.notifyDataSetChanged();
            }
        });
        alertDialog.setOnCancelListener(this);
        alertDialog.show();
        return true;
    }

    public void setItems(List<GradeBean> gradeBeanList, int defaultSelected,MultiSpinnerListener listener) {
        mGradeBeanList.clear();
        Log.e("list",""+gradeBeanList);
        mGradeBeanList.addAll(gradeBeanList);
        this.listener = listener;
        items = new ArrayList<String>();
        // all selected by default
        selected = new boolean[mGradeBeanList.size()];
        for (int i = 0; i < selected.length; i++) {
            if (defaultSelected == i) {
                selected[i] = true;
            } else {
                selected[i] = false;
            }
        }
        for(int i= 0;i<mGradeBeanList.size();i++)
        {
            items.add(mGradeBeanList.get(i).getGradeName());
        }
        // all text on the spinner
       // commented by paramveer

      /* ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.row_spinner, new String[] { mGradeBeanList.get(defaultSelected).getGradeName()});
        setAdapter(adapter);
        performClick();
        alertDialog.cancel();*/
    }


    @Override
    public void onCancel(DialogInterface dialog) {
        StringBuilder spinnerBuilder = new StringBuilder();
        StringBuilder gradeBuilder = new StringBuilder();

        for (int i = 0; i < items.size(); i++) {
            if (selected[i] == true) {
                spinnerBuilder.append(items.get(i));
                spinnerBuilder.append(", ");
                gradeBuilder.append(mGradeBeanList.get(i).getGradeId());
                gradeBuilder.append(", ");
            }
        }
        String spinnerText = "";
        if (spinnerBuilder.length() > 2) {
            spinnerText = spinnerBuilder.substring(0, spinnerBuilder.length() - 2).toString();
            mGrades = spinnerText;
        }
        if (gradeBuilder.length() > 2) {
            mGradeIds = gradeBuilder.substring(0, gradeBuilder.length() - 2).toString();
        }


        //row_spinner_topic
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),R.layout.row_spinner_topic,new String[] { spinnerText });
        setAdapter(adapter);
        listener.onItemsSelected(selected);
    }


    public interface MultiSpinnerListener {
        public void onItemsSelected(boolean[] selected);
    }

    public String getGradeIds() {
        return mGradeIds;
    }
    public String getGrades() {
        return mGrades;
    }

    public void setItems(List<GradeBean> gradeBeanList,List<String> itemValues,String selectedList,MultiSpinnerListener listener) {
        mGradeBeanList.clear();
        Log.e("list",""+gradeBeanList);
        mGradeBeanList.addAll(gradeBeanList);
        this.listener = listener;
        items = new ArrayList<String>();

        for(int i= 0;i<mGradeBeanList.size();i++)
        {
            items.add(mGradeBeanList.get(i).getGradeName());
        }
        // Set false by default
        selected = new boolean[mGradeBeanList.size()];
        for (int j = 0; j < itemValues.size(); j++)
            selected[j] = false;

        String spinnerText = "";
        StringBuilder gradeBuilder= new StringBuilder();
        if (selectedList != null) {
            String[] selectedItems = selectedList.trim().split(",");
            for (int i = 0; i < selectedItems.length; i++)
                for (int j = 0; j < mGradeBeanList.size(); j++)
                    if (selectedItems[i].trim().equals(mGradeBeanList.get(j).getGradeName())) {
                        selected[j] = true;
                        gradeBuilder.append(mGradeBeanList.get(j).getGradeId());
                        gradeBuilder.append(", ");
                        spinnerText += (spinnerText.equals("")?"":", ") + items.get(j);
                        break;
                    }
        }
        mGrades = spinnerText;
        if (gradeBuilder.length() > 2) {
            mGradeIds = gradeBuilder.substring(0, gradeBuilder.length() - 2).toString();
        }
        // Text for the spinner

        //row_spinner_topic
        //row_spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),R.layout.row_spinner_topic, new String[] { spinnerText });
        setAdapter(adapter);
    }
}