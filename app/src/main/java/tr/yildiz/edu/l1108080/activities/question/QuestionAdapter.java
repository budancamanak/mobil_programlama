package tr.yildiz.edu.l1108080.activities.question;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import tr.yildiz.edu.l1108080.R;
import tr.yildiz.edu.l1108080.repository.models.Question;
import tr.yildiz.edu.l1108080.repository.models.QuestionOption;

import java.util.List;

/**
 * Created by y3 on 16/05/2021 22:31.
 */
public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private final Context context;
    List<Question> questions;
    List<QuestionOption> options;
    private QuestionLongClickListener longClickListener;
    private LayoutInflater inflater;


    public QuestionAdapter(Context context, List<Question> questions, List<QuestionOption> options, QuestionLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.questions = questions;
        this.options = options;
    }

    public void setData(List<Question> questions, List<QuestionOption> options) {
        this.questions = questions;
        this.options = options;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.question_list_row, parent, false);
        view.setLongClickable(true);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        Question item = questions.get(position);
        holder.item = item;
        holder.textQuestion.setText(item.text());
        QuestionOption op = getOptionOfWithTag(item.id(), "a");
        if (op != null) {
            holder.txtQA.setText(op.text());
            if (op.correct())
                holder.txtQA.setBackgroundColor(Color.GREEN);
        }
        op = getOptionOfWithTag(item.id(), "b");
        if (op != null) {
            holder.txtQB.setText(op.text());
            if (op.correct())
                holder.txtQB.setTextColor(Color.GREEN);
        }
        op = getOptionOfWithTag(item.id(), "c");
        if (op != null) {
            holder.txtQC.setText(op.text());
            if (op.correct())
                holder.txtQC.setBackgroundColor(Color.GREEN);
        }
        op = getOptionOfWithTag(item.id(), "d");
        if (op != null) {
            holder.txtQD.setText(op.text());
            if (op.correct())
                holder.txtQD.setBackgroundColor(Color.GREEN);
        }
        op = getOptionOfWithTag(item.id(), "e");
        if (op != null) {
            holder.txtQE.setText(op.text());
            if (op.correct())
                holder.txtQE.setBackgroundColor(Color.GREEN);
        }
    }

    QuestionOption getOptionOfWithTag(String qid, String tag) {
        for (QuestionOption item : options) {
            if (!item.questionId().equalsIgnoreCase(qid)) continue;
            if (!item.optionTag().equalsIgnoreCase(tag)) continue;
            return item;
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public interface QuestionLongClickListener {
        void clicked(Question item);
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textQuestion, txtQA, txtQB, txtQC, txtQD, txtQE;
        Question item;

        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textQuestion = itemView.findViewById(R.id.txtQuestion_row);
            txtQA = itemView.findViewById(R.id.txtQA_row);
            txtQB = itemView.findViewById(R.id.txtQB_row);
            txtQC = itemView.findViewById(R.id.txtQC_row);
            txtQD = itemView.findViewById(R.id.txtQD_row);
            txtQE = itemView.findViewById(R.id.txtQE_row);
        }

        @Override
        public void onClick(View v) {
            if (longClickListener != null)
                longClickListener.clicked(item);
        }
    }
}
