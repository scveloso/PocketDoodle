package com.sveloso.pocketdoodle;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.UUID;

/**
 * Created by Veloso on 6/15/2016.
 */
public class ColorPaletteFragment extends Fragment {

    private static final String ARG_COLOR_INT = "color_int";

    private ImageView mCurrentColorImageView;
    private int mCurrentColorInt;
    private PocketDoodleManager sPocketDoodleManager;

    private ImageButton mRedButton;
    private ImageButton mBlueButton;
    private ImageButton mGreenButton;
    private ImageButton mBlackButton;
    private ImageButton mYellowButton;

    private ImageButton mOrangeButton;
    private ImageButton mIndigoButton;
    private ImageButton mVioletButton;

    private ImageButton mWhiteButton;
    private ImageButton mBrownButton;
    private ImageButton mGrayButton;
    private ImageButton mCyanButton;

    private ImageButton mSpringGreenButton;
    private ImageButton mSalmonButton;
    private ImageButton mOliveButton;
    private ImageButton mKhakiButton;

    public static ColorPaletteFragment newInstance(int colorInt) {
        Bundle args = new Bundle();
        args.putInt(ARG_COLOR_INT, colorInt);

        ColorPaletteFragment fragment = new ColorPaletteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentColorInt = getArguments().getInt(ARG_COLOR_INT);
        sPocketDoodleManager = PocketDoodleManager.get(getActivity());
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_color_palette, container, false);

        mCurrentColorImageView = (ImageView) v.findViewById(R.id.current_color);
        updateCurrentColor();

        mRedButton = (ImageButton) v.findViewById(R.id.color_red);
        mRedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentColorInt = ContextCompat.getColor(getActivity(), R.color.colorRed);
                updateCurrentColor();
            }
        });

        mBlueButton = (ImageButton) v.findViewById(R.id.color_blue);
        mBlueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentColorInt = ContextCompat.getColor(getActivity(), R.color.colorBlue);
                updateCurrentColor();
            }
        });

        mGreenButton = (ImageButton) v.findViewById(R.id.color_green);
        mGreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentColorInt = ContextCompat.getColor(getActivity(), R.color.colorGreen);
                updateCurrentColor();
            }
        });

        mBlackButton = (ImageButton) v.findViewById(R.id.color_black);
        mBlackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentColorInt = ContextCompat.getColor(getActivity(), R.color.colorBlack);
                updateCurrentColor();
            }
        });

        mYellowButton = (ImageButton) v.findViewById(R.id.color_yellow);
        mYellowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentColorInt = ContextCompat.getColor(getActivity(), R.color.colorYellow);
                updateCurrentColor();
            }
        });

        mOrangeButton = (ImageButton) v.findViewById(R.id.color_orange);
        mOrangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentColorInt = ContextCompat.getColor(getActivity(), R.color.colorOrange);
                updateCurrentColor();
            }
        });

        mIndigoButton = (ImageButton) v.findViewById(R.id.color_indigo);
        mIndigoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentColorInt = ContextCompat.getColor(getActivity(), R.color.colorIndigo);
                updateCurrentColor();
            }
        });

        mVioletButton = (ImageButton) v.findViewById(R.id.color_violet);
        mVioletButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentColorInt = ContextCompat.getColor(getActivity(), R.color.colorViolet);
                updateCurrentColor();
            }
        });

        mWhiteButton = (ImageButton) v.findViewById(R.id.color_white);
        mWhiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentColorInt = ContextCompat.getColor(getActivity(), R.color.colorWhite);
                updateCurrentColor();
            }
        });

        mBrownButton = (ImageButton) v.findViewById(R.id.color_brown);
        mBrownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentColorInt = ContextCompat.getColor(getActivity(), R.color.colorBrown);
                updateCurrentColor();
            }
        });

        mGrayButton = (ImageButton) v.findViewById(R.id.color_gray);
        mGrayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentColorInt = ContextCompat.getColor(getActivity(), R.color.colorGray);
                updateCurrentColor();
            }
        });

        mCyanButton = (ImageButton) v.findViewById(R.id.color_cyan);
        mCyanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentColorInt = ContextCompat.getColor(getActivity(), R.color.colorCyan);
                updateCurrentColor();
            }
        });

        mSpringGreenButton = (ImageButton) v.findViewById(R.id.color_springgreen);
        mSpringGreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentColorInt = ContextCompat.getColor(getActivity(), R.color.colorSpringGreen);
                updateCurrentColor();
            }
        });


        mSalmonButton = (ImageButton) v.findViewById(R.id.color_salmon);
        mSalmonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentColorInt = ContextCompat.getColor(getActivity(), R.color.colorSalmon);
                updateCurrentColor();
            }
        });
        mOliveButton = (ImageButton) v.findViewById(R.id.color_olive);
        mOliveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentColorInt = ContextCompat.getColor(getActivity(), R.color.colorOlive);
                updateCurrentColor();
            }
        });
        mKhakiButton = (ImageButton) v.findViewById(R.id.color_khaki);
        mKhakiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentColorInt = ContextCompat.getColor(getActivity(), R.color.colorKhaki);
                updateCurrentColor();
            }
        });

        return v;
    }

    private void updateCurrentColor() {
        mCurrentColorImageView.setBackgroundColor(mCurrentColorInt);
        Paint newPaint = new Paint();
        newPaint.setColor(mCurrentColorInt);
        sPocketDoodleManager.setPaint(newPaint);
    }
}
