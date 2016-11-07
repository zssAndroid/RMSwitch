package com.rm.rmswitch;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Riccardo Moro on 29/07/2016.
 */
public class RMSwitch extends RMAbstractSwitch {
    private static final String BUNDLE_KEY_CHECKED = "bundle_key_checked";
    private static final String BUNDLE_KEY_DESIGN = "bundle_key_design";
    private static final String BUNDLE_KEY_FORCE_ASPECT_RATIO = "bundle_key_force_aspect_ratio";
    private static final String BUNDLE_KEY_BKG_CHECKED_COLOR = "bundle_key_bkg_checked_color";
    private static final String BUNDLE_KEY_BKG_NOT_CHECKED_COLOR =
            "bundle_key_bkg_not_checked_color";
    private static final String BUNDLE_KEY_TOGGLE_CHECKED_COLOR = "bundle_key_toggle_checked_color";
    private static final String BUNDLE_KEY_TOGGLE_NOT_CHECKED_COLOR =
            "bundle_key_toggle_not_checked_color";
    private static final String BUNDLE_KEY_TOGGLE_CHECKED_DRAWABLE_RES =
            "bundle_key_toggle_checked_drawable_res";
    private static final String BUNDLE_KEY_TOGGLE_NOT_CHECKED_DRAWABLE_RES =
            "bundle_key_toggle_not_checked_drawable_res";

    private static final float SWITCH_STANDARD_ASPECT_RATIO = 2.2f;


    // View variables
    private List<RMSwitchObserver> mObservers;

    /**
     * The current switch state
     */
    private boolean mIsChecked;

    /**
     * The switch background color when is checked
     */
    private int mBkgCheckedColor;

    /**
     * The switch background color when is not checked
     */
    private int mBkgNotCheckedColor;

    /**
     * The toggle color when checked
     */
    private int mToggleCheckedColor;

    /**
     * The toggle color when not checked
     */
    private int mToggleNotCheckedColor;

    /**
     * The checked toggle drawable resource
     */
    private int mToggleCheckedDrawableResource;

    /**
     * The not checked toggle drawable resource
     */
    private int mToggleNotCheckedDrawableResource;


    public RMSwitch(Context context) {
        this(context, null);
    }

    public RMSwitch(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RMSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = (Bundle) super.onSaveInstanceState();

        bundle.putBoolean(BUNDLE_KEY_CHECKED, mIsChecked);

        bundle.putInt(BUNDLE_KEY_BKG_CHECKED_COLOR, mBkgCheckedColor);
        bundle.putInt(BUNDLE_KEY_BKG_NOT_CHECKED_COLOR, mBkgNotCheckedColor);

        bundle.putInt(BUNDLE_KEY_TOGGLE_CHECKED_COLOR, mToggleCheckedColor);
        bundle.putInt(BUNDLE_KEY_TOGGLE_NOT_CHECKED_COLOR, mToggleNotCheckedColor);

        bundle.putInt(BUNDLE_KEY_TOGGLE_CHECKED_DRAWABLE_RES, mToggleCheckedDrawableResource);
        bundle.putInt(BUNDLE_KEY_TOGGLE_NOT_CHECKED_DRAWABLE_RES,
                mToggleNotCheckedDrawableResource);

        return bundle;
    }

    @SuppressWarnings("WrongConstant")
    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
        Bundle prevState =  (Bundle) state;

        // Restore the check state notifying the observers
        mBkgCheckedColor = prevState.getInt(BUNDLE_KEY_BKG_CHECKED_COLOR,
                Utils.getDefaultBackgroundColor(getContext()));
        mBkgNotCheckedColor = prevState.getInt(BUNDLE_KEY_BKG_NOT_CHECKED_COLOR,
                mBkgCheckedColor);

        mToggleCheckedColor = prevState.getInt(BUNDLE_KEY_TOGGLE_CHECKED_COLOR,
                Utils.getAccentColor(getContext()));
        mToggleNotCheckedColor = prevState.getInt(BUNDLE_KEY_TOGGLE_NOT_CHECKED_COLOR,
                Color.WHITE);

        mToggleCheckedDrawableResource = prevState
                .getInt(BUNDLE_KEY_TOGGLE_CHECKED_DRAWABLE_RES, 0);
        mToggleNotCheckedDrawableResource = prevState
                .getInt(BUNDLE_KEY_TOGGLE_NOT_CHECKED_DRAWABLE_RES, mToggleCheckedDrawableResource);

        setChecked(prevState.getBoolean(BUNDLE_KEY_CHECKED, false));
        notifyObservers();
    }


    // Setup programmatically the appearance

    public void setSwitchBkgCheckedColor(@ColorInt int color) {
        mBkgCheckedColor = color;
        setupSwitchAppearance();
    }

    public void setSwitchBkgNotCheckedColor(@ColorInt int color) {
        mBkgNotCheckedColor = color;
        setupSwitchAppearance();
    }

    public void setSwitchToggleCheckedColor(@ColorInt int color) {
        mToggleCheckedColor = color;
        setupSwitchAppearance();
    }

    public void setSwitchToggleNotCheckedColor(@ColorInt int color) {
        mToggleNotCheckedColor = color;
        setupSwitchAppearance();
    }

    public void setSwitchToggleCheckedDrawableRes(@DrawableRes int drawable) {
        mToggleCheckedDrawableResource = drawable;
        setupSwitchAppearance();
    }

    public void setSwitchToggleNotCheckedDrawableRes(@DrawableRes int drawable) {
        mToggleNotCheckedDrawableResource = drawable;
        setupSwitchAppearance();
    }


    // Get the switch setup
    @ColorInt
    public int getSwitchBkgCheckedColor() {
        return mBkgCheckedColor;
    }

    @ColorInt
    public int getSwitchBkgNotCheckedColor() {
        return mBkgNotCheckedColor;
    }

    @ColorInt
    public int getSwitchToggleCheckedColor() {
        return mToggleCheckedColor;
    }

    @ColorInt
    public int getSwitchToggleNotCheckedColor() {
        return mToggleNotCheckedColor;
    }

    @DrawableRes
    public int getSwitchToggleCheckedDrawableRes() {
        return mToggleCheckedDrawableResource;
    }

    @DrawableRes
    public int getSwitchToggleNotCheckedDrawableRes() {
        return mToggleNotCheckedDrawableResource;
    }

    /**
     * Adds an observer to the list {@link #mObservers}
     *
     * @param switchObserver The observer to be added
     */
    public void addSwitchObserver(RMSwitchObserver switchObserver) {
        if (switchObserver == null)
            return;

        if (mObservers == null)
            mObservers = new ArrayList<>();

        mObservers.add(switchObserver);
    }

    /**
     * Searches and removes the passed {@link RMSwitchObserver}
     * from the observers list {@link #mObservers}
     *
     * @param switchObserver The observer to be removed
     */
    public void removeSwitchObserver(RMSwitchObserver switchObserver) {
        if (switchObserver != null &&// Valid RMSwitchObserverPassed
                mObservers != null && mObservers.size() > 0 && // Observers list initialized and
                // not empty
                mObservers.indexOf(switchObserver) >= 0) {// new Observer found in the list
            mObservers.remove(mObservers.indexOf(switchObserver));
        }
    }

    /**
     * Notify all the registered observers
     */
    private void notifyObservers() {
        if (mObservers != null) {
            for (RMSwitchObserver observer : mObservers) {
                observer.onCheckStateChange(this, mIsChecked);
            }
        }
    }

    /**
     * Removes all the observer from {@link #mObservers}
     */
    public void removeSwitchObservers() {
        if (mObservers != null && mObservers.size() > 0)
            mObservers.clear();
    }

    /**
     * Setup all the switch custom attributes appearance
     */
    @Override
    public void setupSwitchAppearance() {
        // Create the background drawables
        Drawable bkgDrawable =
                ContextCompat.getDrawable(getContext(), R.drawable.rounded_border_bkg);
        ((GradientDrawable) bkgDrawable).setColor(
                mIsChecked
                        ? mBkgCheckedColor
                        : mBkgNotCheckedColor);

        // Create the toggle drawables
        Drawable toggleDrawable =
                mIsChecked
                        // If checked
                        ? mToggleCheckedDrawableResource != 0
                        ? ContextCompat.getDrawable(getContext(),
                        mToggleCheckedDrawableResource)
                        : null
                        // If not checked
                        : mToggleNotCheckedDrawableResource != 0
                        ? ContextCompat.getDrawable(getContext(),
                        mToggleNotCheckedDrawableResource)
                        : null;


        // Create the toggle background drawables
        Drawable toggleBkgDrawable =
                ContextCompat.getDrawable(getContext(), R.drawable.rounded_border_bkg);
        ((GradientDrawable) toggleBkgDrawable).setColor(mIsChecked
                ? mToggleCheckedColor
                : mToggleNotCheckedColor);

        // Set the background drawable
        if (mImgBkg.getDrawable() != null) {
            // Create the transition for the background
            TransitionDrawable bkgTransitionDrawable = new TransitionDrawable(new Drawable[]{
                    // If it was a transition drawable, take the last one of it's drawables
                    mImgBkg.getDrawable() instanceof TransitionDrawable ?
                            ((TransitionDrawable) mImgBkg.getDrawable()).getDrawable(1) :
                            mImgBkg.getDrawable(),
                    bkgDrawable
            });
            bkgTransitionDrawable.setCrossFadeEnabled(true);
            // Set the transitionDrawable and start the animation
            mImgBkg.setImageDrawable(bkgTransitionDrawable);
            bkgTransitionDrawable.startTransition(ANIMATION_DURATION);
        } else {
            // No previous background image, just set the new one
            mImgBkg.setImageDrawable(bkgDrawable);
        }

        // Set the toggle background
        if (mImgToggle.getBackground() != null) {
            // Create the transition for the background of the toggle
            TransitionDrawable toggleBkgTransitionDrawable =
                    new TransitionDrawable(new Drawable[]{
                            // If it was a transition drawable, take the last one of it's drawables
                            mImgToggle.getBackground() instanceof TransitionDrawable ?
                                    ((TransitionDrawable) mImgToggle.getBackground()).getDrawable
                                            (1) :
                                    mImgToggle.getBackground(),
                            toggleBkgDrawable
                    });
            toggleBkgTransitionDrawable.setCrossFadeEnabled(true);
            // Set the transitionDrawable and start the animation
            mImgToggle.setBackground(toggleBkgTransitionDrawable);
            toggleBkgTransitionDrawable.startTransition(ANIMATION_DURATION);
        } else {
            // No previous background image, just set the new one
            mImgToggle.setImageDrawable(toggleBkgDrawable);
        }

        // Set the toggle image
        if (mImgToggle.getDrawable() != null) {
            // Create the transition for the image of the toggle
            TransitionDrawable toggleTransitionDrawable = new TransitionDrawable(new Drawable[]{
                    // If it was a transition drawable, take the last one of it's drawables
                    mImgToggle.getDrawable() instanceof TransitionDrawable ?
                            ((TransitionDrawable) mImgToggle.getDrawable()).getDrawable(1) :
                            mImgToggle.getDrawable(),
                    toggleDrawable
            });
            toggleTransitionDrawable.setCrossFadeEnabled(true);
            // Set the transitionDrawable and start the animation
            mImgToggle.setImageDrawable(toggleTransitionDrawable);
            toggleTransitionDrawable.startTransition(ANIMATION_DURATION);
        } else {
            // No previous toggle image, just set the new one
            mImgToggle.setImageDrawable(toggleDrawable);
        }

        setSwitchAlpha();
    }

    @Override
    public void setupSwitchCustomAttributes(TypedArray typedArray) {
        // Get the checked flag
        mIsChecked = typedArray.getBoolean(
                R.styleable.RMSwitch_checked, false);

        // Keep aspect ratio flag
        mForceAspectRatio = typedArray.getBoolean(
                R.styleable.RMSwitch_forceAspectRatio, true);

        // If the switch is enabled
        mIsEnabled = typedArray.getBoolean(
                R.styleable.RMSwitch_enabled, true);


        //Get the background checked and not checked color
        mBkgCheckedColor = typedArray.getColor(
                R.styleable.RMSwitch_switchBkgCheckedColor,
                Utils.getDefaultBackgroundColor(getContext()));

        mBkgNotCheckedColor = typedArray.getColor(
                R.styleable.RMSwitch_switchBkgNotCheckedColor,
                mBkgCheckedColor);


        //Get the toggle checked and not checked colors
        mToggleCheckedColor = typedArray.getColor(
                R.styleable.RMSwitch_switchToggleCheckedColor,
                Utils.getAccentColor(getContext()));

        mToggleNotCheckedColor = typedArray.getColor(
                R.styleable.RMSwitch_switchToggleNotCheckedColor,
                Color.WHITE);


        // Get the toggle checked and not checked images
        mToggleCheckedDrawableResource = typedArray.getResourceId(
                R.styleable.RMSwitch_switchToggleCheckedImage, 0);
        mToggleNotCheckedDrawableResource = typedArray.getResourceId(
                R.styleable.RMSwitch_switchToggleNotCheckedImage,
                mToggleCheckedDrawableResource);

        // If set the not checked drawable and not the checked one, copy the first
        if (mToggleCheckedDrawableResource == 0 && mToggleNotCheckedDrawableResource != 0)
            mToggleCheckedDrawableResource = mToggleNotCheckedDrawableResource;

        // Set manually checked flag, update the appearance and change the toggle gravity
        setChecked(mIsChecked);
    }

    /**
     * Move the toggle from one side to the other of this view,
     * called AFTER setting the {@link #mIsChecked} variable
     */
    @Override
    protected void changeToggleGravity() {

        LayoutParams toggleParams =
                ((LayoutParams) mImgToggle.getLayoutParams());

        // Add the new alignment rule
        toggleParams.addRule(
                getCurrentLayoutRule());

        // Remove the previous alignment rule
        removeRule(toggleParams, getPreviousLayoutRule());

        mImgToggle.setLayoutParams(toggleParams);
    }

    // Get the current layout rule to display the toggle in its correct position
    private int getCurrentLayoutRule() {
        return
                mIsChecked ?
                        ALIGN_PARENT_RIGHT :
                        ALIGN_PARENT_LEFT;
    }

    // Get the previous layout rule based on the current state and the toggle direction
    private int getPreviousLayoutRule() {
        return mIsChecked ?
                ALIGN_PARENT_LEFT :
                ALIGN_PARENT_RIGHT;
    }

    // Checkable interface methods
    @Override
    public void setChecked(boolean checked) {
        mIsChecked = checked;

        setupSwitchAppearance();
        changeToggleGravity();
    }

    @Override
    public boolean isChecked() {
        return mIsChecked;
    }

    @Override
    public float getSwitchAspectRatio() {
        return SWITCH_STANDARD_ASPECT_RATIO;
    }

    @Override
    public int getSwitchStandardWidth() {
        return R.dimen.rm_switch_standard_width;
    }

    @Override
    public int getSwitchStandardHeight() {
        return R.dimen.rm_switch_standard_height;
    }

    @Override
    public void toggle() {
        setChecked(!mIsChecked);
        notifyObservers();
    }

    // Public interface to watch the check state change
    public interface RMSwitchObserver {
        void onCheckStateChange(RMSwitch switchView, boolean isChecked);
    }

    @Override
    public int[] getTypedArrayResource() {
        return R.styleable.RMSwitch;
    }
}
