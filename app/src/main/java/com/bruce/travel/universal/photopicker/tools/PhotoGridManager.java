package com.bruce.travel.universal.photopicker.tools;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.bruce.travel.R;
import com.bruce.travel.universal.photopicker.camera.PhotoPickManger;
import com.bruce.travel.universal.photopicker.view.SquareImageView;

import java.io.File;
import java.util.List;

/**
 * Created by Lin on 16/5/18.
 */

public class PhotoGridManager {

    public interface OnItemAction {
        public void onItemAciton(PhotoGridManager photoGridManager);
    }

//    protected GalleryDialog galleryDialog;
    protected PhotoPickManger pickManger;
    protected int maxCount;
    protected int numColumns;
    protected Context context;
    protected GridView gridView;
    protected int layoutView = R.layout.adapter_photo_grid_item;
    protected SimpleGenericAdapter<File> adapter;
    /**
     * xianghuan
     */



    private int drawableAdd;
    private int drawableRest;
    private int drawableDel;

    private OnItemAction onItemAddAction;
    private OnItemAction onItemDelAction;

    protected int temp;

    public PhotoGridManager(GridView gridView, PhotoPickManger mPickManger, int maxCount, int numColumns) {
        this.gridView = gridView;
        this.maxCount = maxCount;
        this.numColumns = numColumns;
        gridView.setNumColumns(numColumns);
        this.context = gridView.getContext();
        this.pickManger = mPickManger;
//        galleryDialog = new GalleryDialog(context);
//        galleryDialog.setOnDeleteFileisten(new GalleryDialog.OnDeleteFileListen() {
//            @Override
//            public void onDeleteFile(List<File> files, File file, int position) {
//                adapter.notifyDataSetChanged();
//            }
//        });
        initAdaper();
        gridView.setAdapter(adapter);

    }

    private void initAdaper() {
        adapter = new SimpleGenericAdapter<File>(context, pickManger.getSelectsPhotos(), layoutView) {

            @Override
            public int getCount() {
                temp = getPickManger().getSelectsPhotos().size() + 1;
                return temp > maxCount ? maxCount : temp;
            }


            @Override
            public ViewHolder getViewHolder(final File file) {
                return new ViewHolder(file) {
                    View view;
                    SquareImageView siv;
                    Button del;

                    @Override
                    public SimpleGenericAdapter.ViewHolder getHolder(View view) {
                        this.view = view;
                        siv = (SquareImageView) view.findViewById(R.id.image);
                        del = (Button) view.findViewById(R.id.btn_del);
                        del.setVisibility(View.GONE);
                        del.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (getPositopn() < pickManger.getSelectsPhotos().size()) {
                                    pickManger.getSelectsPhotos().remove(getPositopn());
                                    notifyDataSetChanged();
                                    if (onItemDelAction != null) {
                                        onItemDelAction.onItemAciton(PhotoGridManager.this);
                                    }
                                }
                            }
                        });
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (getPositopn() >= pickManger.getSelectsPhotos().size() && getPositopn() < maxCount) {
                                    if (onItemAddAction != null) {
                                        onItemAddAction.onItemAciton(PhotoGridManager.this);
                                    }
                                }else
                                {
//                                        galleryDialog.startFile(pickManger.getSelectsPhotos(), getPositopn(), true);
                                }

                            }
                        });
//                        view.setOnLongClickListener(new View.OnLongClickListener() {
//                            @Override
//                            public boolean onLongClick(View v) {
//                                isShakeAnim = true;
//                                notifyDataSetChanged();
//                                return false;
//                            }
//                        });
                        return this;
                    }

                    @Override
                    public void show() {
                        del.setVisibility(View.GONE);
                        if (getPositopn() == pickManger.getSelectsPhotos().size()) {
                            SimpleImageLoader.displayFromDrawable(drawableAdd, siv);
                        } else if (getPositopn() > pickManger.getSelectsPhotos().size()) {
                            SimpleImageLoader.displayFromDrawable(drawableDel, siv);
                        } else {
                            if (getPositopn() < pickManger.getSelectsPhotos().size()) {
                                SimpleImageLoader.displayImage(pickManger.getSelectsPhotos().get(getPositopn()), siv);
                                del.setVisibility(View.VISIBLE);
                                del.setBackgroundResource(drawableDel);
                            }
                        }


                    }
                };
            }
        };
    }

    public PhotoPickManger getPickManger() {
        return pickManger;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public PhotoGridManager setMaxCount(int maxCoun) {
        this.maxCount = maxCoun;
        return this;
    }

    public Context getContext() {
        return context;
    }

    public PhotoGridManager setContext(Context context) {
        this.context = context;
        return this;
    }

    public GridView getGridView() {
        return gridView;
    }

    public PhotoGridManager setGridView(GridView gridView) {
        this.gridView = gridView;
        return this;
    }

    public int getLayoutView() {
        return layoutView;
    }

    public PhotoGridManager setLayoutView(int layoutView) {
        this.layoutView = layoutView;
        return this;
    }

    public SimpleGenericAdapter<File> getAdapter() {
        return adapter;
    }

    public PhotoGridManager setAdapter(SimpleGenericAdapter<File> adapter) {
        this.adapter = adapter;
        return this;
    }

//    public GalleryDialog getGalleryDialog() {
//        return galleryDialog;
//    }
//
//    public PhotoGridManager setGalleryDialog(GalleryDialog galleryDialog) {
//        this.galleryDialog = galleryDialog;
//        return this;
//    }



    public int getNumColumns() {
        return numColumns;
    }

    public PhotoGridManager setNumColumns(int numColumns) {
        this.numColumns = numColumns;
        return this;
    }


    public int getDrawableAdd() {
        return drawableAdd;
    }

    public PhotoGridManager setDrawableAdd(int drawableAdd) {
        this.drawableAdd = drawableAdd;
        return this;
    }

    public int getDrawableRest() {
        return drawableRest;
    }

    public PhotoGridManager setDrawableRest(int drawableRest) {
        this.drawableRest = drawableRest;
        return this;
    }

    public int getDrawableDel() {
        return drawableDel;
    }

    public PhotoGridManager setDrawableDel(int drawableDel) {
        this.drawableDel = drawableDel;
        return this;
    }

    public OnItemAction getOnItemAddAction() {
        return onItemAddAction;
    }

    public PhotoGridManager setOnItemAddAction(OnItemAction onItemAddAction) {
        this.onItemAddAction = onItemAddAction;
        return this;
    }

    public OnItemAction getOnItemDelAction() {
        return onItemDelAction;
    }

    public PhotoGridManager setOnItemDelAction(OnItemAction onItemDelAction) {
        this.onItemDelAction = onItemDelAction;
        return this;
    }

    public int getNum() {
        return temp;
    }
}

