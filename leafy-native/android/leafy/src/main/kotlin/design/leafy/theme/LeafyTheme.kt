package design.leafy.theme

import android.app.UiModeManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import design.leafy.R
import design.leafy.sound.LeafySound
import design.leafy.sound.LocalLeafySound

/** IBM Plex, bundled in res/font under the SIL Open Font License 1.1 (see IBM-Plex-OFL.txt). */
object LeafyPlex {
    val Sans = FontFamily(
        Font(R.font.ibm_plex_sans_light, FontWeight.Light),
        Font(R.font.ibm_plex_sans_regular, FontWeight.Normal),
        Font(R.font.ibm_plex_sans_medium, FontWeight.Medium),
        Font(R.font.ibm_plex_sans_semibold, FontWeight.SemiBold),
        Font(R.font.ibm_plex_sans_bold, FontWeight.Bold),
    )

    val Mono = FontFamily(
        Font(R.font.ibm_plex_mono_regular, FontWeight.Normal),
        Font(R.font.ibm_plex_mono_semibold, FontWeight.SemiBold),
    )
}

/** The three font roles in tokens.json: brand and plain are IBM Plex Sans, mono is IBM Plex Mono. */
@Immutable
data class LeafyFonts(
    val brand: FontFamily = LeafyPlex.Sans,
    val plain: FontFamily = LeafyPlex.Sans,
    val mono: FontFamily = LeafyPlex.Mono,
) {
    companion object {
        /** The bundled IBM Plex. */
        val Default = LeafyFonts()

        /** The device's own fonts at Leafy's sizes and weights, if an app must not ship Plex. */
        val System = LeafyFonts(FontFamily.Default, FontFamily.Default, FontFamily.Monospace)
    }
}

/**
 * Material 3 shape slots set to the corners Leafy's components use (Carbon squares them),
 * so stock Material components come out right. The raw values are in [LeafyRadius].
 */
val LeafyShapes = Shapes(
    extraSmall = RoundedCornerShape(LeafyRadius.shapeCornerExtraSmall), // text fields, menus, snackbars, tooltips
    small = RoundedCornerShape(LeafyRadius.shapeCornerSmall), // chips (Leafy's own chips are pills: CircleShape)
    medium = RoundedCornerShape(LeafyRadius.shapeCornerSmall), // cards, small FAB
    large = RoundedCornerShape(LeafyRadius.shapeCornerMedium), // FAB, navigation drawer
    extraLarge = RoundedCornerShape(LeafyRadius.shapeCornerMedium), // dialogs, bottom sheets, large FAB
)

val LocalLeafyColors = staticCompositionLocalOf { LeafyLightColors }
val LocalLeafyTypography = staticCompositionLocalOf { leafyTypography() }

/** Leafy's values inside [LeafyTheme]: `Leafy.colors.success`, `Leafy.typography.code02`, `Leafy.sound.play(...)`. */
object Leafy {
    val colors: LeafyColors
        @Composable @ReadOnlyComposable get() = LocalLeafyColors.current

    val typography: LeafyTypography
        @Composable @ReadOnlyComposable get() = LocalLeafyTypography.current

    val sound: LeafySound
        @Composable @ReadOnlyComposable get() = LocalLeafySound.current
}

/**
 * Applies Leafy: the colour theme for dark mode and contrast, the type scale, Leafy's shapes and the sound player.
 * Material 3 components inside it pick up Leafy's roles; Leafy components add the Carbon pieces.
 *
 * @param highContrast Defaults to the Android 14+ contrast setting (medium or high picks the high-contrast themes).
 */
@Composable
fun LeafyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    highContrast: Boolean = isSystemHighContrast(),
    fonts: LeafyFonts = LeafyFonts.Default,
    content: @Composable () -> Unit,
) {
    val colors = when {
        darkTheme && highContrast -> LeafyDarkHighContrastColors
        darkTheme -> LeafyDarkColors
        highContrast -> LeafyLightHighContrastColors
        else -> LeafyLightColors
    }
    val colorScheme = remember(colors, darkTheme) { colors.toColorScheme(darkTheme) }
    val typography = remember(fonts) { leafyTypography(fonts) }
    val context = LocalContext.current
    val preview = LocalInspectionMode.current
    val sound = remember(context, preview) { if (preview) LeafySound.Silent else LeafySound.get(context) }
    CompositionLocalProvider(
        LocalLeafyColors provides colors,
        LocalLeafyTypography provides typography,
        LocalLeafySound provides sound,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography.toMaterial(),
            shapes = LeafyShapes,
            content = content,
        )
    }
}

/** True when the Android 14+ contrast setting is medium or high. Always false before Android 14. */
@Composable
fun isSystemHighContrast(): Boolean =
    if (Build.VERSION.SDK_INT >= 34) systemContrast() >= 0.5f else false

@RequiresApi(34)
@Composable
private fun systemContrast(): Float {
    val context = LocalContext.current
    val manager = remember(context) { context.getSystemService(UiModeManager::class.java) } ?: return 0f
    var contrast by remember(manager) { mutableFloatStateOf(manager.contrast) }
    DisposableEffect(manager) {
        val listener = UiModeManager.ContrastChangeListener { contrast = it }
        manager.addContrastChangeListener(context.mainExecutor, listener)
        onDispose { manager.removeContrastChangeListener(listener) }
    }
    return contrast
}

/** Carbon's motion: productive curves for everyday UI, expressive ones for moments that deserve attention. */
object LeafyMotion {
    /** Toggles, state changes, menus, chevrons. */
    val Productive = CubicBezierEasing(0.2f, 0f, 0.38f, 0.9f)

    /** A dialog entering, a toast arriving, a page transition. */
    val Expressive = CubicBezierEasing(0.4f, 0.14f, 0.3f, 1f)

    const val FAST_01 = 70
    const val FAST_02 = 110
    const val MODERATE_01 = 150
    const val MODERATE_02 = 240
    const val SLOW_01 = 400
    const val SLOW_02 = 700
}
