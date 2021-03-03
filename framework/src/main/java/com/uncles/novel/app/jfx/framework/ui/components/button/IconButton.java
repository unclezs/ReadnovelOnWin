package com.uncles.novel.app.jfx.framework.ui.components.button;

import com.jfoenix.controls.JFXButton;
import com.uncles.novel.app.jfx.framework.ui.components.icon.Icon;
import com.uncles.novel.app.jfx.framework.ui.components.icon.SvgIcon;
import javafx.beans.property.StringProperty;
import javafx.css.CssMetaData;
import javafx.css.SimpleStyleableStringProperty;
import javafx.css.Styleable;
import javafx.css.StyleableStringProperty;
import javafx.css.converter.StringConverter;
import javafx.scene.Cursor;
import javafx.scene.control.Tooltip;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 图标按钮封装
 * <p>
 * 支持svg和fontawesome
 * <p>
 * css设置图标
 * <pre>
 *     1.字体图标 {@link com.uncles.novel.app.jfx.framework.ui.components.icon.Icon}
 *     -fx-icon: "\uf010"
 *     -fx-icon: "图标名"
 *     2.矢量图标 {@link com.uncles.novel.app.jfx.framework.ui.components.icon.SvgIcon}
 *     -fx-svg-icon: "_图标名"
 *     -fx-svg-icon: "svg path"
 * </pre>
 *
 * @author blog.unclezs.com
 * @date 2021/02/28 1:28
 */
public class IconButton extends JFXButton {
    public static final String DEFAULT_STYLE_CLASS = "icon-button";
    /**
     * 悬浮提示
     */
    @Getter
    private String tip;

    /**
     * 字体图标css
     */
    private StringProperty icon;

    /**
     * svg图标css
     */
    private StringProperty svg;


    public IconButton() {
        this(null);
    }

    /**
     * 设置文字
     *
     * @param text 文字
     */
    public IconButton(String text) {
        this(text, null, null);
    }

    /**
     * 可以设置提示
     *
     * @param text 文本
     * @param tip  提示
     */
    public IconButton(String text, String tip) {
        this(text, null, tip);
    }

    /**
     * 设置全部
     *
     * @param text 文字
     * @param icon 字体图标
     * @param tip  提示
     */
    public IconButton(String text, String icon, String tip) {
        super(text);
        getStyleClass().add(DEFAULT_STYLE_CLASS);
        setText(text);
        setIcon(icon);
        setTip(tip);
        setCursor(Cursor.HAND);
    }

    /**
     * 获取svg
     *
     * @return svg
     */
    public String getSvg() {
        return svgProperty().get();
    }

    /**
     * 设置svg图标
     *
     * @param svg svg图标
     */
    public void setSvg(String svg) {
        this.svgProperty().set(svg);
    }

    /**
     * 获取字体图标
     *
     * @return 字体图标
     */
    public String getIcon() {
        return iconProperty().get();
    }

    /**
     * 设置字体图标
     *
     * @param icon 字体图标
     */
    public void setIcon(String icon) {
        this.iconProperty().set(icon);
    }

    /**
     * 设置字体图标
     *
     * @param icon 字体图标
     */
    public void setIcon(Icon icon) {
        if (icon != null) {
            this.iconProperty().set(icon.getIcon().toString());
            this.setGraphic(icon);
        }
    }

    /**
     * 设置悬浮提示
     *
     * @param tip 提示
     */
    public void setTip(String tip) {
        this.tip = tip;
        if (tip != null) {
            this.setTooltip(new Tooltip(tip));
        }
    }

    public StringProperty iconProperty() {
        if (icon == null) {
            icon = new SimpleStyleableStringProperty(StyleableProperties.ICON, IconButton.this, "-fx-icon") {
                @Override
                public void invalidated() {
                    if (getGraphic() != null && getGraphic() instanceof Icon) {
                        Icon icon = (Icon) getGraphic();
                        icon.setIcon(getValue());
                    } else {
                        setGraphic(new Icon(getValue()));
                    }
                }
            };
        }
        return icon;
    }

    public StringProperty svgProperty() {
        if (svg == null) {
            svg = new SimpleStyleableStringProperty(StyleableProperties.SVG, IconButton.this, "-fx-svg-icon") {
                @Override
                public void invalidated() {
                    if (getGraphic() != null && getGraphic() instanceof SvgIcon) {
                        SvgIcon icon = (SvgIcon) getGraphic();
                        icon.setPath(getValue());
                    } else {
                        setGraphic(new SvgIcon(getValue()));
                    }
                }
            };
        }
        return svg;
    }

    /**
     * 自定义css属性
     * <p>
     * 1.-fx-icon指定图标
     * <p>
     * 2.-fx-svg-icon指定svg图标
     */
    private static class StyleableProperties {
        private static final CssMetaData<IconButton, String> ICON = new CssMetaData<>("-fx-icon", StringConverter.getInstance()) {
            @Override
            public boolean isSettable(IconButton control) {
                return control.icon == null || !control.icon.isBound();
            }

            @Override
            public StyleableStringProperty getStyleableProperty(IconButton control) {
                return (StyleableStringProperty) control.icon;
            }
        };
        private static final CssMetaData<IconButton, String> SVG = new CssMetaData<>("-fx-svg-icon", StringConverter.getInstance()) {
            @Override
            public boolean isSettable(IconButton control) {
                return control.svg == null || !control.svg.isBound();
            }

            @Override
            public StyleableStringProperty getStyleableProperty(IconButton control) {
                return (StyleableStringProperty) control.svg;
            }
        };
        private static final List<CssMetaData<? extends Styleable, ?>> CHILD_STYLEABLES;

        static {
            final List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(JFXButton.getClassCssMetaData());
            Collections.addAll(styleables, ICON, SVG);
            CHILD_STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
        return getClassCssMetaData();
    }


    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return StyleableProperties.CHILD_STYLEABLES;
    }
}
