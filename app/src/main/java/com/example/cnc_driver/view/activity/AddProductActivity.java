package com.example.cnc_driver.view.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.cnc_driver.R;
import com.example.cnc_driver.common.Constacts;
import com.example.cnc_driver.common.eventBus.EventBusAction;
import com.example.cnc_driver.common.eventBus.ImageEvent;
import com.example.cnc_driver.common.eventBus.ProductUpdateEvent;
import com.example.cnc_driver.net.FirebaseManager;
import com.example.cnc_driver.net.ImageFirebaseUtils;
import com.example.cnc_driver.net.response.ProductResponse;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;

public class AddProductActivity extends BaseActivity {

    @BindView(R.id.imgCategory)
    ImageView imgCategory;
    @BindView(R.id.tvTitleCategory)
    TextView tvTitleCategory;
    @BindView(R.id.tvCategory)
    TextView tvCategory;
    @BindView(R.id.imgCategoryNext)
    ImageView imgCategoryNext;
    @BindView(R.id.rlCategory)
    RelativeLayout rlCategory;
    @BindView(R.id.imgPrice)
    ImageView imgPrice;
    @BindView(R.id.tvTitlePrice)
    TextView tvTitlePrice;
    @BindView(R.id.tvPrice)
    EditText tvPrice;
    @BindView(R.id.rlPrice)
    RelativeLayout rlPrice;
    @BindView(R.id.lnImage)
    LinearLayout lnImage;
    @BindView(R.id.img_camera)
    ImageView imgCamera;
    @BindView(R.id.edtNameProduct)
    EditText edtNameProduct;
    @BindView(R.id.edtDetail)
    EditText edtDetail;
    @BindView(R.id.lnDetail)
    LinearLayout lnDetail;
    @BindView(R.id.tvFinish)
    TextView tvFinish;
    @BindView(R.id.rlParent)
    RelativeLayout rlParent;
    @BindView(R.id.img_delete_image)
    ImageView imgDeleteImage;
    private Uri uriImage;
    private String image;
    private String id_category;
    private FirebaseManager firebaseManager = new FirebaseManager();
    private AlertDialog waitingDialog;
    private String newString = "0";
    private ProductResponse productResponse;

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_add_product;
    }

    @Override
    protected void initialize(@Nullable Bundle savedInstanceState) {
        this.waitingDialog = new SpotsDialog.Builder().setMessage("Loading").setContext(this).build();
        setStatus();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }

    private void filebasechooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, Constacts.REQUEST_CODE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constacts.REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            waitingDialog.show();
            uriImage = data.getData();
            ImageFirebaseUtils.fileUploader(this, uriImage);
            imgCamera.setImageURI(uriImage);
            imgDeleteImage.setVisibility(View.VISIBLE);
        }
        if (requestCode == Constacts.REQUEST_CODE_CHOOSE_CATEGORY && resultCode == RESULT_OK) {
            id_category = data.getStringExtra("result");
            assert id_category != null;
            switch (id_category) {
                case "1":
                    tvCategory.setText(R.string.title_milk_tea);
                    break;
                case "2":
                    tvCategory.setText(R.string.title_fruit);
                    break;
                case "3":
                    tvCategory.setText(R.string.title_bread);
                    break;
            }

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleLoginEvent(ImageEvent event) throws IOException {
        switch (event.action) {
            case EventBusAction.IMAGESUCCESS:
            case EventBusAction.IMAGEFAILL:
                image = event.uri;
                waitingDialog.dismiss();
                break;
            default:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void handleUpdateProductEvent(ProductUpdateEvent event) throws IOException {
        switch (event.action) {
            case EventBusAction.PRODUCT_UPDATE:
                productResponse = (ProductResponse) event.object;
                if (newString.equals("1")) {
                    setUpData(productResponse);
                }
                break;
            default:
                break;
        }
    }

    @OnClick({R.id.rlCategory, R.id.img_camera, R.id.tvFinish, R.id.img_delete_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rlCategory:
                startActivityForResult(new Intent(this, ChooseCategoryActivity.class), Constacts.REQUEST_CODE_CHOOSE_CATEGORY);
                break;
            case R.id.img_camera:
                filebasechooseImage();
                break;
            case R.id.tvFinish:
                if (tvFinish.getText().toString().equals(getString(R.string.key_update))){
                    updateProduct();
                }else {
                    addProduct();
                }
                break;
            case R.id.img_delete_image:
                imgCamera.setImageResource(R.drawable.ic_camera);
                imgDeleteImage.setVisibility(View.GONE);
                image = "";
                break;
        }
    }

    private void addProduct() {
        if (validate()) {
            String name = edtNameProduct.getText().toString();
            String detail = edtDetail.getText().toString();
            String price;
            if (tvPrice.getText().toString().equals("")) {
                price = "0";
            } else {
                price = tvPrice.getText().toString();
            }
            firebaseManager.insertProduct(name, id_category, image, price, detail);
            finish();
        }
    }

    private void updateProduct() {
        if (validate()) {
            String name = edtNameProduct.getText().toString();
            String detail = edtDetail.getText().toString();
            String price;
            if (tvPrice.getText().toString().equals("")) {
                price = "0";
            } else {
                price = tvPrice.getText().toString();
            }
            productResponse.setImage(image);
            productResponse.setName(name);
            productResponse.setDescription(detail);
            productResponse.setPrice(price);
            firebaseManager.updateproduct(productResponse.getId(), productResponse);
            finish();
        }
    }

    private boolean validate() {
        if (id_category == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getString(R.string.key_validate_category));
            builder.show();
            return false;
        }
        if (image == null && image.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getString(R.string.key_validate_image));
            builder.show();
            return false;
        }
        if (edtNameProduct.getText().toString().equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getString(R.string.key_validate_name));
            builder.show();
            return false;
        }
        return true;
    }

    public void setStatus() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            newString = null;
        } else {
            newString = extras.getString(Constacts.KEY_UPDATE);
            tvFinish.setText(getString(R.string.key_update));
        }
    }

    public void setUpData(ProductResponse productResponse) {
        if (productResponse != null) {
            tvPrice.setText(productResponse.getPrice());
            Picasso.get().load(productResponse.getImage()).into(imgCamera);
            imgDeleteImage.setVisibility(View.VISIBLE);
            image = productResponse.getImage();
            edtNameProduct.setText(productResponse.getName());
            edtDetail.setText(productResponse.getDescription());
            id_category = productResponse.getCategory_id();
            switch (id_category) {
                case "1":
                    tvCategory.setText(R.string.title_milk_tea);
                    break;
                case "2":
                    tvCategory.setText(R.string.title_fruit);
                    break;
                case "3":
                    tvCategory.setText(R.string.title_bread);
                    break;
            }
        }
    }
}
