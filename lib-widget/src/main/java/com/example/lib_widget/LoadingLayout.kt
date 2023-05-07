package com.example.lib_widget

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import androidx.core.view.isVisible
import com.example.lib_ktx.viewbinding.binding
import com.example.lib_widget.databinding.LayoutLoadingLayoutBinding

class LoadingLayout : FrameLayout {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        initAttrs(context, attrs)
        initView()
    }

    private val binding: LayoutLoadingLayoutBinding by binding(true)

    private lateinit var currentState: State

    //空数据状态
    @DrawableRes
    private var emptyIcon: Int = com.example.resources.R.drawable.resources_empty_v1
    private var emptyTips: String? = null
    private val defaultEmptyTips = "暂无数据"

    //错误状态
    @DrawableRes
    private var errorIcon: Int = com.example.resources.R.drawable.resources_load_fail
    private var errorTips: String? = null
    private val defaultErrorTips = "出错啦"

    //网络异常
    @DrawableRes
    private var netWorkIcon: Int = com.example.resources.R.drawable.resources_network_error
    private var netWorkTips: String? = null
    private val defaultNetworkTips = "没有网络"

    private fun initAttrs(context: Context, attrs: AttributeSet?) {
        attrs?.let {
            val typeArray = context.obtainStyledAttributes(
                it, R.styleable.LoadingLayout
            )
            try {
                emptyTips = typeArray.getString(R.styleable.LoadingLayout_loading_empty_tip)
                emptyIcon =
                    typeArray.getResourceId(
                        R.styleable.LoadingLayout_loading_empty_icon,
                        com.example.resources.R.drawable.resources_empty_v1
                    )

                errorTips = typeArray.getString(R.styleable.LoadingLayout_loading_error_tip)
                errorIcon =
                    typeArray.getResourceId(
                        R.styleable.LoadingLayout_loading_error_icon,
                        com.example.resources.R.drawable.resources_load_fail
                    )

                netWorkTips = typeArray.getString(R.styleable.LoadingLayout_loading_network_tip)
                netWorkIcon =
                    typeArray.getResourceId(
                        R.styleable.LoadingLayout_loading_network_icon,
                        com.example.resources.R.drawable.resources_network_error
                    )
            } finally {
                typeArray.recycle()
            }
        }
    }

    private fun initView() {
        currentState = State.Loading
        setState(State.Normal)
    }

    /**
     * 设置状态
     */
    fun setState(state: State) {
        when (state) {
            State.Loading -> showLoadingState()
            State.Normal -> showNormalState()
            State.EmptyData -> showEmptyDataState()
            State.NetworkError -> showNetErrorState()
            State.Error -> showErrorState()
        }
    }


    /**
     * 显示Loading状态
     */
    fun showLoadingState() {
        if (currentState == State.Loading) {
            return
        }
        currentState = State.Loading
        isVisible = true
        binding.groupOfStatic.isVisible = false
        binding.lottieLoad.isVisible = true
        binding.lottieLoad.playAnimation()
    }

    /**
     * 显示正常状态
     */
    fun showNormalState() {
        if (currentState == State.Normal) {
            return
        }
        currentState = State.Normal
        isVisible = false
        binding.lottieLoad.cancelAnimation()
    }

    /**
     * 显示空数据状态
     * @param tip 提示文案
     * @param icon 空数据显示文案
     */
    fun showEmptyDataState(
        tip: String? = emptyTips,
        @DrawableRes icon: Int = emptyIcon,
    ) {
        if (currentState == State.EmptyData) {
            return
        }
        currentState = State.EmptyData
        isVisible = true
        emptyTips = tip
        emptyIcon = icon
        binding.ivDefault.setImageResource(emptyIcon)
        binding.tvTips.text = if (emptyTips.isNullOrEmpty()) defaultEmptyTips else emptyTips
        binding.groupOfStatic.isVisible = true
        binding.lottieLoad.isVisible = false
        binding.lottieLoad.cancelAnimation()
    }

    /**
     * 显示请求异常信息
     * @param tip 提示文案
     * @param icon 空数据显示文案
     */
    fun showErrorState(
        tip: String? = errorTips,
        @DrawableRes icon: Int = errorIcon,
    ) {
        if (currentState == State.Error) {
            return
        }
        currentState = State.Error
        isVisible = true
        errorTips = tip
        errorIcon = icon
        binding.ivDefault.setImageResource(errorIcon)
        binding.tvTips.text = if (errorTips.isNullOrEmpty()) defaultErrorTips else errorTips
        binding.groupOfStatic.isVisible = true
        binding.lottieLoad.isVisible = false
        binding.lottieLoad.cancelAnimation()
    }

    /**
     * 显示请求异常信息
     * @param tip 提示文案
     * @param icon 空数据显示文案
     */
    fun showNetErrorState(
        tip: String? = errorTips,
        @DrawableRes icon: Int = errorIcon,
    ) {
        if (currentState == State.NetworkError) {
            return
        }
        currentState = State.NetworkError
        isVisible = true
        netWorkTips = tip
        netWorkIcon = icon
        binding.ivDefault.setImageResource(netWorkIcon)
        binding.tvTips.text = if (netWorkTips.isNullOrEmpty()) defaultNetworkTips else netWorkTips
        binding.groupOfStatic.isVisible = true
        binding.lottieLoad.isVisible = false
        binding.lottieLoad.cancelAnimation()
    }


    enum class State {
        //加载状态
        Loading,

        //正常，不显示当前控件
        Normal,

        //空数据，
        EmptyData,

        //网络异常
        NetworkError,

        //错误异常
        Error
    }
}