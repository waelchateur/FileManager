package ru.file.manager.recycler;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import java.io.File;
import ru.file.manager.R;
import ru.file.manager.data.Preferences;
import ru.file.manager.utils.FileUtils;
import ru.file.manager.utils.PreferenceUtils;

final class ViewHolderImage extends ViewHolder {
    private TextView name;
    private TextView date;

    ViewHolderImage(Context context, OnItemClickListener listener, View view) {
        super(context, listener, view);
    }

    @Override
    protected void loadIcon() {
        image = itemView.findViewById(R.id.list_item_image);
    }

    @Override
    protected void loadName() {
        name = itemView.findViewById(R.id.list_item_name);
    }

    @Override
    protected void loadInfo() {
        date = itemView.findViewById(R.id.list_item_date);
    }

    @Override
    protected void bindIcon(File file, Boolean selected) {
        final int color = ContextCompat.getColor(context, FileUtils.getColorResource(file));
        Glide.with(context).load(file).asBitmap().fitCenter().into(new BitmapImageViewTarget(image) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> animation) {
                this.view.setImageBitmap(resource);
                name.setBackgroundColor(Palette.from(resource).generate().getMutedColor(color));
            }
        });
    }

    @Override
    protected void bindName(File file) {
        name.setText(Preferences.showExtensions() ? FileUtils.getName(file) : file.getName());
    }

    @Override
    protected void bindInfo(File file) {
        if (date == null) return;
        date.setText(FileUtils.getLastModified(file));
    }
}
