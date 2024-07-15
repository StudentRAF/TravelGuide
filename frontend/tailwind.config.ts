/*
 * Copyright (C) 2024. Nemanja Radovanovic
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/** @type {import('tailwindcss').Config} */
module.exports = {
  darkMode: ["class"],
  content: [
    './pages/**/*.{ts,tsx}',
    './components/**/*.{ts,tsx}',
    './app/**/*.{ts,tsx}',
    './src/**/*.{ts,tsx}',
  ],
  prefix: "",
  theme: {
    borderRadius: {
      none:   "0",       // 0px
      small:  "0.25rem", // 4px
      normal: "0.5rem",  // 8px
      medium: "0.75rem", // 12px
      large:  "1.25rem", // 20px
      full:   "625rem",  // 10_000px
    },
    fontFamily: {
      sans: "DM Sans, sans-serif"
    },
    fontSize: {
      small:   ["0.8125rem", "1.125rem"], // 13px 18px
      normal:  ["0.875rem",  "1.25rem"],  // 14px 20px
      large:   ["1rem",      "1.5rem"],   // 16px 24px
      title:   ["1.25rem",   "1.75rem"],  // 20px 28px
      heading: ["1.5rem",    "2rem"],     // 24px 32px
    },
    colors: {
      transparent: "transparent",
      white: "#FFFFFF",
      black: "#000000",
      gray: {
        DEFAULT: "#7E7E7E",
        50:  "#EFEFEF",
        100: "#D6D6D6",
        150: "#CBCBCB",
        200: "#C0C0C0",
        250: "#B5B5B5",
        300: "#AAAAAA",
        350: "#9F9F9F",
        400: "#949494",
        450: "#898989",
        500: "#7E7E7E",
        550: "#737373",
        600: "#686868",
        650: "#5D5D5D",
        700: "#525252",
        750: "#474747",
        800: "#3C3C3C",
        850: "#313131",
        900: "#262626",
        950: "#101010"
      },
      metal: {
        DEFAULT: "#7E7F80",
        50:  "#EFF0F1",
        100: "#D6D7D7",
        200: "#C0C1C2",
        300: "#AAABAC",
        400: "#949596",
        500: "#7E7F80",
        600: "#68696A",
        700: "#525354",
        800: "#3C3D3E",
        900: "#262727",
        950: "#101112"
      },
      blue: {
        DEFAULT: "#2E90FA",
        50:  "#EFF4FF",
        100: "#D1E0FF",
        200: "#B2CCFF",
        300: "#84ADFF",
        400: "#3B7CFF",
        500: "#2970FF",
        600: "#155EEF",
        700: "#004EEB",
        800: "#0040C1",
        900: "#00359E",
        950: "#002266"
      },
      orange: {
        DEFAULT: "#FA9005",
        50:  "#FEE9CD",
        100: "#FDD5A0",
        200: "#FCC173",
        300: "#FBAF4B",
        400: "#FB9F28",
        500: "#FA9005",
        600: "#E08105",
        700: "#C77204",
        800: "#9E5B03",
        900: "#754402",
        950: "#4D2C02"
      },
      purple: {
        DEFAULT: "#A644F3",
        50:  "#EAD4FC",
        100: "#DAB2FA",
        200: "#CB91F8",
        300: "#BD74F6",
        400: "#B15CF4",
        500: "#A644F3",
        600: "#943DD9",
        700: "#8336BF",
        800: "#692B99",
        900: "#4E2073",
        950: "#34164D"
      },
      red: {
        DEFAULT: "#F92424",
        50:  "#FECDCD",
        100: "#FDA6A6",
        200: "#FC8383",
        300: "#FB6060",
        400: "#FA4242",
        500: "#F92424",
        600: "#DE2020",
        700: "#BF1C1C",
        800: "#9C1616",
        900: "#751111",
        950: "#4D0B0B"
      },
      green: {
        DEFAULT: "#32B830",
        50:  "#D7F4D7",
        100: "#AFEAAE",
        200: "#87DF86",
        300: "#63D661",
        400: "#43CD41",
        500: "#32B830",
        600: "#2DA62B",
        700: "#289427",
        800: "#238021",
        900: "#1C661B",
        950: "#154D14"
      },
      yellow: {
        DEFAULT: "#F3ED25",
        50:  "#FCFBCF",
        100: "#FAF8A8",
        200: "#F8F581",
        300: "#F6F25F",
        400: "#F5EF42",
        500: "#F3ED25",
        600: "#D9D321",
        700: "#BAB61C",
        800: "#999517",
        900: "#737011",
        950: "#4D4B0C"
      }
    },
    extend: {
      spacing: {
        140: "35rem", // 640px
        160: "40rem", // 640px
        200: "50rem", // 800px
        300: "75rem", // 1200px
      },
      keyframes: {
        "accordion-down": {
          from: { height: "0" },
          to: { height: "var(--radix-accordion-content-height)" },
        },
        "accordion-up": {
          from: { height: "var(--radix-accordion-content-height)" },
          to: { height: "0" },
        },
      },
      animation: {
        "accordion-down": "accordion-down 0.2s ease-out",
        "accordion-up": "accordion-up 0.2s ease-out",
      },
      boxShadow: {
        normal: "-2px 2px  8px 0px rgba(0, 0, 0, 0.25)",
        medium: "-2px 2px 10px 0px rgba(0, 0, 0, 0.25)",
        large:  "-3px 3px 12px 0px rgba(0, 0, 0, 0.25)"
      },
    },
  },
  plugins: [require("tailwindcss-animate")],
}