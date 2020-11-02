package com.sunfb.sideindexletter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.sunfb.sideindexletter.adapter.CityAdapter;
import com.sunfb.sideindexletter.bean.CityBean;
import com.sunfb.sideindexletter.decoration.DividerItemDecoration;
import com.sunfb.sideindexletter.decoration.SectionItemDecoration;
import com.sunfb.sideindexletter.pinyinhelper.PinyinHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SideIndexLetter sideIndexLetter;
    private TextView tv_touch_letter;
    private EditText et_search;
    private RecyclerView rv_province;
    private String[] province;
    private CityAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private List<CityBean> allCities = new ArrayList<>();
    private List<CityBean> mResults = new ArrayList<>();
    private PinyinHelper mPinyinHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPinyinHelper = new PinyinHelper();
        init();

    }

    public void init() {
        sideIndexLetter = findViewById(R.id.index_letter);
        tv_touch_letter = findViewById(R.id.tv_touch_letter);
        et_search = findViewById(R.id.et_search);
        rv_province = findViewById(R.id.rv_province);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String keyword = s.toString();
                if (TextUtils.isEmpty(keyword)){
                    ((SectionItemDecoration)(rv_province.getItemDecorationAt(0))).setData(allCities);
                    adapter.updateData(allCities);
                } else {
                    mResults.clear();
                    // 判断数据中是否含有所输入的字符，将含有该字符的集合转换、排序、更新列表
                    Log.i("xht","afterTextChanged()--keyword==" + keyword);
                    for(int i = 0; i < allCities.size(); i++) {
                        if(allCities.get(i).getName().contains(keyword)) {
                            mResults.add(allCities.get(i));
                            Log.i("xht","result1==" + allCities.get(i));
                        } else if(allCities.get(i).getIndexTag().equals(keyword.toUpperCase())) {
                            mResults.add(allCities.get(i));
                            Log.i("xht","result2==" + allCities.get(i));
                        }
                    }
                    // 使用tinypinyin转换、排序
                    mPinyinHelper.sortSourceDatas(mResults);
                    ((SectionItemDecoration)(rv_province.getItemDecorationAt(0))).setData(mResults);
                    adapter.updateData(mResults);
                }
            }
        });
        sideIndexLetter.setOverlayTextView(tv_touch_letter).setListener(new SideIndexLetter.ISideIndexLetterTouchListener() {
            @Override
            public void onTouchAction(String touchedLetterText, int position) {
                if (allCities != null && allCities.size() > 0) {
                    int size = allCities.size();
                    for (int i = 0; i < size; i++) {
                        if (TextUtils.equals(touchedLetterText.substring(0, 1), allCities.get(i).getIndexTag().substring(0, 1))) {
                            if (linearLayoutManager != null) {
                                linearLayoutManager.scrollToPositionWithOffset(i, 0);
                                return;
                            }
                        }
                    }
                }
            }
        });
        province = getResources().getStringArray(R.array.provinces);
        initData(province);
        adapter = new CityAdapter(this, allCities);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_province.setLayoutManager(linearLayoutManager);
        rv_province.addItemDecoration(new SectionItemDecoration(this,allCities),0);
        rv_province.addItemDecoration(new DividerItemDecoration(this,allCities),1);
        rv_province.setAdapter(adapter);


    }


    public void initData(String[] province) {

        if (province == null || province.length == 0) return;
        for (String cityName : province) {
            CityBean cityBean = new CityBean();
            cityBean.setName(cityName);
            allCities.add(cityBean);

        }
        mPinyinHelper.sortSourceDatas(allCities);

    }
}