package app.view.com;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2018/10/7.
 */

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {

    private List<RVBean> mData = new ArrayList<>();
    private Context mContext;
    onItemViewClick mOnItemViewClick;
    onClickListener mOnClickListener;
    onItemViewLongClick mOnItemViewLongClick;
    onLongClickListener mOnLongClickListener;

    View HeardView;
    View FooterView;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_NORMAL = 2;

    @Override
    public int getItemViewType(int position) {
        if (HeardView == null && FooterView == null) {
            return TYPE_NORMAL;
        }
        if (position == 0) {
            return TYPE_HEADER;
        }
        if (position == getItemCount() - 1) {
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }


    public void setOnItemViewClick(onItemViewClick onItemViewClick) {
        mOnItemViewClick = onItemViewClick;
    }

    public void setOnClickListener(onClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public void setOnItemViewLongClick(onItemViewLongClick onItemViewLongClick) {
        mOnItemViewLongClick = onItemViewLongClick;
    }

    public void setOnLongClickListener(onLongClickListener onLongClickListener) {
        mOnLongClickListener = onLongClickListener;
    }

    RvAdapter(List<RVBean> data, Context context) {
        this.mData = data;
        this.mContext = context;

    }

    interface onItemViewClick {
        void onItemViewClick(View view, int position);
    }

    interface onClickListener {
        void onClickListener(View view, int position);
    }

    interface onItemViewLongClick {
        void onItemViewLongClick(View view, int position);
    }

    interface onLongClickListener {
        void onLongClickListener(View view, int position);
    }

    //在指定位置插入，原位置的向后移动一格
    public boolean addItem(int position, RVBean msg) {
        if (position < mData.size() && position >= 0) {
            mData.add(position, msg);
            notifyItemInserted(position);
            return true;
        }
        return false;
    }

    //去除指定位置的子项
    public boolean removeItem(int position) {
        if (position < mData.size() && position >= 0) {
            mData.remove(position);
            notifyItemRemoved(position);
            return true;
        }
        return false;
    }

    //清空显示数据
    public void clearAll() {
        mData.clear();
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (HeardView != null && viewType == TYPE_HEADER) {
            return new ViewHolder(HeardView);
        }
        if (FooterView != null && viewType == TYPE_FOOTER) {
            return new ViewHolder(FooterView);
        }

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_tv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            holder.mTextView.setText(mData.get(position - 1).getText());
            holder.mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClickListener.onClickListener(holder.itemView, position);
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemViewClick.onItemViewClick(holder.itemView, position);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemViewLongClick.onItemViewLongClick(holder.itemView, position);
                    return true;
                }
            });
            holder.mTextView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnLongClickListener.onLongClickListener(holder.itemView, position);
                    return true;
                }
            });
            return;
        } else if (getItemViewType(position) == TYPE_HEADER) {
            return;
        } else {
            return;
        }
    }

    @Override
    public int getItemCount() {
        if (HeardView == null && FooterView == null) {
            return mData.size();
        } else if (HeardView == null && FooterView != null) {
            return mData.size() + 1;
        } else if (HeardView != null && FooterView == null) {
            return mData.size() + 1;
        } else {
            return mData.size() + 2;
        }
    }

    public View getHeaderView() {
        return HeardView;
    }

    public void setHeaderView(View headerView) {
        this.HeardView = headerView;
        notifyItemInserted(0);
    }

    public View getFooterView() {
        return FooterView;
    }

    public void setFooterView(View footerView) {
        this.FooterView = footerView;
        notifyItemInserted(getItemCount() - 1);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        ViewHolder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.item_tv);
        }
    }
}

