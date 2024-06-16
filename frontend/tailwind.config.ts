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
    container: {
      center: true,
      padding: "2rem",
      screens: {
        "2xl": "1400px",
      },
    },
    borderRadius: {
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
      }

    },
    extend: {
      spacing: {
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
    },
  },
  plugins: [require("tailwindcss-animate")],
}