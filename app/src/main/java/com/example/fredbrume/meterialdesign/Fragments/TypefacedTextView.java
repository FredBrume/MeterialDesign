package com.example.fredbrume.meterialdesign.Fragments;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.fredbrume.meterialdesign.R;

/**
 * Created by fredbrume on 11/5/17.
 */

public class TypefacedTextView extends TextView {

    public TypefacedTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        if(isInEditMode()){
            return;
        }

        TypedArray styleAttrs = context.obtainStyledAttributes(attrs, R.styleable.TypefacedTextView);
        String fontName= styleAttrs.getString(R.styleable.TypefacedTextView_typeface);
        styleAttrs.recycle();

        if(fontName != null)
        {
            Typeface typeface = Typeface.createFromAsset(context.getAssets(),fontName);
            setTypeface(typeface);
        }
    }

}
