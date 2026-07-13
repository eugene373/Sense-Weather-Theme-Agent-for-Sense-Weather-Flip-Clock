# Sense Weather Theme Template Guide

Welcome to the official developer guide for creating custom themes for Sense Weather. This guide provides an exhaustive reference for every resource, component, and system controlled by the Theme Engine.

## ⚖️ Legal & Licensing Notice (TCLA)
By downloading, using, modifying, or compiling any asset within this repository, you explicitly acknowledge and agree to be automatically bound by the **Sense Weather Theme Creator License Agreement (TCLA)** located in the `License Agreement (TCLA).md` file. 

### Key Developer Terms:
* **Intellectual Property**: You retain the copyright ownership to your original custom artwork, icons, graphics, and typography styles.
* **Licensor Rights**: You grant the platform a perpetual, irrevocable, royalty-free, worldwide license to use, modify, bundle, and distribute your themes (free or paid) within the Sense Weather application ecosystem without financial compensation.
* **Prohibition of Advertisements**: Themes **must not** contain, bundle, call, or execute third-party advertisement frameworks, tracking pixels, monetization scripts, or advertisement SDKs. Violation will result in immediate termination of platform compatibility.
* **Automatic Binding**: The physical act of compiling or building theme source code into an executable package (.zip, .apk, .aab) triggers immediate, automated acceptance of the TCLA.

---

## 🛠 How the Theme Engine Works

The Sense Weather Theme Engine is a provider-based system that allows the app to dynamically override its visual identity. Instead of using static resource IDs, the app requests resources by **name strings** (e.g., `"bg_day_start"`).

### Resource Resolution Flow
1. **Request**: The app asks for a resource by name.
2. **Theme Lookup**: The `ThemeResourceProvider` searches the active theme's resource table (via `getIdentifier`).
3. **Result**: 
   - If found: The theme's resource is used.
   - If NOT found: The app falls back to a hardcoded default resource.

This "Null-Fallback" behavior means you only need to provide the resources you actually want to change; you don't need to implement every single key for a theme to work.

---

## 📦 Theme Types

### 1. External Themes (APK)
The most powerful way to distribute themes. An external theme is a lightweight Android APK that the main app detects upon installation.
- **Pros**: Distributable via Play Store/GitHub; supports full Android resource optimization.
- **Requirement**: Must include a specific `<meta-data>` tag in the `AndroidManifest.xml` for discovery.

### 2. Bundled Themes (Assets)
Themes stored directly within the app's `assets/themes/` folder.
- **Pros**: Instant loading; no installation required.
- **Requirement**: Must follow the specific folder structure within the assets directory.

---

## 📁 Folder Structure

### External Theme (APK) Structure
Follow the standard Android project structure. The critical parts are:

```text
ThemeProject/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── AndroidManifest.xml       <-- Must contain discovery meta-data
│   │       ├── assets/
│   │       │   └── manifest.json         <-- Theme metadata (REQUIRED)
│   │       └── res/
│   │           ├── values/
│   │           │   ├── colors.xml        <-- Theme colors
│   │           │   ├── dimens.xml        <-- Widget offsets
│   │           │   └── strings.xml       <-- Branding & text
│   │           ├── drawable/             <-- Weather icons, backgrounds, clock
│   │           ├── anim/                 <-- Custom animations
│   │           ├── layout/               <-- UI structure overrides
│   │           └── font/                 <-- Custom typography (Optional)
```

### Bundled Theme Structure
If adding a theme to the app's assets:
`assets/themes/{theme_id}/`
- `manifest.json` (REQUIRED)
- `preview/` (Optional: `icon.png`, `screenshot_1.png`)
- `fonts/` (Optional: `.ttf` or `.otf` files)
- Other resource files as needed.

---

## 📄 The Theme Manifest (`manifest.json`)

The `manifest.json` is the heart of your theme. It tells the app what the theme is called, what it supports, and how to map fonts.

### Example Manifest
```json
{
  "id": "ocean_breeze",
  "name": "Ocean Breeze",
  "schema_version": 1,
  "min_app_version": "1.0.0",
  "accent_color": "#FF669900",
  "preview_icon": "preview/icon.png",
  "preview_screenshots": ["screenshot_1.png"],
  "supported_widgets": ["widget_24", "widget_44"],
  "supported_layouts": ["main", "settings"],
  "fonts": {
    "primary": "fonts/main_font.ttf",
    "secondary": "fonts/accent_font.otf"
  }
}
```

### Field Reference
| Field | Type | Required | Description |
| :--- | :--- | :--- | :--- |
| `id` | String | **Yes** | Unique identifier used for persistence. |
| `name` | String | **Yes** | Display name shown in the theme browser. |
| `schema_version`| Int | No | Current version is `1`. |
| `min_app_version`| String | No | Ensures the app version is compatible. |
| `accent_color` | Hex | No | Default highlight color (e.g., `#FF669900`). |
| `preview_icon` | Path | No | Path to the icon used in the theme selector. |
| `fonts` | Map | No | Mapping of font IDs to asset paths. |

---

## 🎨 Theming API (Exhaustive Resource Reference)

To override a resource, name your resource in `colors.xml`, `drawables/`, etc., exactly as listed below.

### 1. Color Keys (`res/values/colors.xml`)
| Key | Purpose |
| :--- | :--- |
| `htc_green` | Primary brand accent color (Sense Green) |
| `htc_dark_green` | Darker brand accent color |
| `bg_day_start` | Starting color for the daytime background gradient |
| `bg_day_end` | Ending color for the daytime background gradient |
| `bg_night_start` | Starting color for the nighttime background gradient |
| `bg_night_end` | Ending color for the nighttime background gradient |
| `bg_sunset_start` | Starting color for the sunset background gradient |
| `bg_sunset_end` | Ending color for the sunset background gradient |
| `text_primary` | Main foreground text color |
| `text_secondary` | Secondary/muted foreground text color |
| `text_shadow` | Color of the text shadow/glow effect |
| `widget_bg` | Background color for home screen widgets |
| `card_bg` | Background color for information cards in the app |
| `clock_card_bg` | Background color specifically for the flip clock card |
| `widget_text_color` | Text color specifically for the widgets |

### 2. Drawable Keys (`res/drawable/`)

#### **Weather Icons (PNG)**
Replace these pairs (Day and Night variants) to change weather symbols.
- `weather_clear.png` / `weather_clear_night.png`
- `weather_cloudy_day.png` / `weather_cloudy_night.png`
- `weather_partly_sunny.png` / `weather_partly_cloud_night.png`
- `weather_fog.png` / `weather_fog_night.png`
- `weather_heavy_rain.png` / `weather_heavy_rain_night.png`
- `weather_heavy_rain_showers.png` / `weather_heavy_rain_showers_night.png`
- `weather_light_rain.png` / `weather_light_rain_night.png`
- `weather_light_drizzle.png` / `weather_light_drizzle_night.png`
- `weather_heavy_snow.png` / `weather_heavy_snow_night.png`
- `weather_heavy_snow_showers.png` / `weather_heavy_snow_showers_night.png`
- `weather_overcast.png` / `weather_overcast_night.png`
- `weather_thunderstorm_hail.png` / `weather_thunderstorm_hail_night.png`
- `weather_thunderstorm_heavy_hail.png` / `weather_thunderstorm_heavy_hail_night.png`
- `weather_unknown.png` (Fallback)

#### **Weather Backgrounds (JPG)**
- `weather_clear_bg.jpg` / `weather_clear_night_bg.jpg`
- `weather_cloudy_day_bg.jpg` / `weather_cloudy_night_bg.jpg`
- `weather_fog_day_bg.jpg` / `weather_fog_night_bg.jpg`
- `weather_partly_cloud_bg.jpg` / `weather_partly_cloud_night_bg.jpg`
- `weather_partly_sunny_bg.jpg`
- `weather_rain_day_bg.jpg` / `weather_rain_night_bg.jpg`
- `weather_snow_day_bg.jpg` / `weather_snow_night_bg.jpg`
- `weather_thunderstorm_day_bg.jpg` / `weather_thunderstorm_night_bg.jpg`

#### **Moon Phases (PNG)**
- `moon_new.png`, `moon_waxingcrescent.png`, `moon_waxinggibbous.png`, `moon_firstquarter.png`, `moon_waninggibbous.png`, `moon_lastquarter.png`, `moon_waningcrescent.png`, `moon_full.png`

#### **Flip Clock & UI (PNG)**
- `flip_0.png` through `flip_9.png` (Digits)
- `flip_am.png`, `flip_pm.png`
- `appwidget_bg.png` (Widget base background)
- `weather_clock_tab.png` (Clock/Weather toggle tab)

#### **Animation Particles (PNG)**
- `drop_01.png` to `drop_08.png` (Rain drops)
- `snow_01.png` to `snow_08.png` (Snowflakes)
- `rain_01.png` to `rain_03.png` (Rain streaks)
- `lightning_01.png` to `lightning_03.png` (Lightning bolts)
- `flake_01.png` to `flake_03.png` (Large flakes)
- `glare.png` (Light flare)

### 3. Dimension Keys (`res/values/dimens.xml`)
Controls positioning of elements in widgets. Positive = shift in named direction; Negative = shift opposite.

| Widget | Element | Offsets (Left, Top, Right, Top) |
| :--- | :--- | :--- |
| **2x4** | Location | `widget24_location_offset_*` |
| **2x4** | Condition | `widget24_condition_offset_*` |
| **2x4** | Feels Like | `widget24_feelslike_offset_*` |
| **2x4** | Wind | `widget24_wind_offset_*` |
| **2x4** | Date | `widget24_date_offset_*` |
| **2x4** | Temp | `widget24_temp_offset_*` |
| **2x4** | High/Low | `widget24_high_offset_*`, `widget24_low_offset_*` |
| **2x4** | High/Low Val | `widget24_hightemp_offset_*`, `widget24_lowtemp_offset_*` |
| **4x4** | All above | Replace `widget24` with `widget44` |

### 4. Animation Files (`res/anim/`)
Standard Android XML animations.
- `fade_in.xml`, `fade_out.xml`
- `flip_in.xml`, `flip_out.xml`
- `grow_from_bottom.xml`, `grow_from_top.xml`, `grow_from_bottomleft_to_topright.xml`, `grow_from_bottomright_to_topleft.xml`, `grow_from_topleft_to_bottomright.xml`, `grow_from_topright_to_bottomleft.xml`
- `shrink_from_bottom.xml`, `shrink_from_top.xml`, `shrink_from_bottomleft_to_topright.xml`, `shrink_from_bottomright_to_topleft.xml`, `shrink_from_topleft_to_bottomright.xml`, `shrink_from_topright_to_bottomleft.xml`
- `boing.xml`, `bail.xml`

### 5. Layout Overrides (`res/layout/`)
You can replace the XML structure of these key components:
- `activity_theme_info.xml` (Theme preview/info screen)
- `widget_base.xml` (Root widget structure)
- `widget_loading.xml` (Widget loading state)
- `widget_flip_digit.xml` (Single flip-digit layout)
- `widget_24_home_flip.xml` (2x4 Home Widget layout)
- `widget_44_forecast.xml` (4x4 Forecast Widget layout)

### 6. String Overrides (`res/values/strings.xml`)
- `app_name`, `theme_name`, `theme_author`, `theme_description`, `widget_description`
- `loading`, `search_hint`, `no_data`
- `wind_format`, `humidity_format`, `uv_format`, `last_updated`
- `pollen`, `pollen_index_format`, `high_low_format`

---

## 🚀 External Theme Requirements

If you are building an APK theme, you **must** include the following in your `AndroidManifest.xml`:

```xml
<application>
    <meta-data 
        android:name="com.eugene373.sense.weather.THEME" 
        android:value="your_theme_id_here" />
</application>
```

**Important**: Ensure your `applicationId` in `build.gradle.kts` is unique.

---

## 💡 Best Practices & Troubleshooting

- **Alpha Channels**: Use semi-transparent colors (`#AARRGGBB`) for cards to maintain depth.
- **Aspect Ratios**: Keep weather icons at 64×64 PNG to prevent stretching.
- **Theme Visibility**: If your theme doesn't appear, verify the `manifest.json` is in the root of the `assets` folder and the meta-data tag is correct.
- **Fallback**: If a resource is missing, Sense Weather uses the default. This allows you to only override what you need.
