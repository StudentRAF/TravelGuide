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

import { extendTailwindMerge } from "tailwind-merge";

const fontFamily   : string[] = ["sans"];
const fontSize     : string[] = ["small", "normal", "large", "title", "heading"];
const boxShadow    : string[] = ["normal", "medium", "large"];
const borderRadius : string[] = ["none", "small", "normal", "medium", "large", "full"];
const spacing      : string[] = [ "140", "160", "200", "300" ];

export const twMerge = extendTailwindMerge({
  extend: {
    theme: {
      gap: spacing,
      padding: spacing,
      space: spacing,
      translate: spacing,
      inset: spacing,
      margin: spacing
    },
    classGroups: {
      // Border Radius
      rounded: [{
        rounded: borderRadius
      }],

      // Border Radius Start
      'rounded-s': [{
        'rounded-s': borderRadius
      }],

      // Border Radius End
      'rounded-e': [{
        'rounded-e': borderRadius
      }],

      // Border Radius Top
      'rounded-t': [{
        'rounded-t': borderRadius
      }],

      // Border Radius Right
      'rounded-r': [{
        'rounded-r': borderRadius
      }],

      // Border Radius Bottom
      'rounded-b': [{
        'rounded-b': borderRadius
      }],

      // Border Radius Left
      'rounded-l': [{
        'rounded-l': borderRadius
      }],

      // Border Radius Start Start
      'rounded-ss': [{
        'rounded-ss': borderRadius
      }],

      // Border Radius Start End
      'rounded-se': [{
        'rounded-se': borderRadius
      }],

      // Border Radius End End
      'rounded-ee': [{
        'rounded-ee': borderRadius
      }],

      // Border Radius End Start
      'rounded-es': [{
        'rounded-es': borderRadius
      }],

      // Border Radius Top Left
      'rounded-tl': [{
        'rounded-tl': borderRadius
      }],

      // Border Radius Top Right
      'rounded-tr': [{
        'rounded-tr': borderRadius
      }],

      // Border Radius Bottom Left
      'rounded-bl': [{
        'rounded-bl': borderRadius
      }],

      // Border Radius Bottom Right
      'rounded-br': [{
        'rounded-br': borderRadius
      }],

      // Width
      w: [{
        w: spacing,
      }],

      // Min-Width
      'min-w': [{
        'min-w': spacing,
      }],

      // Max-Width
      'max-w': [{
        'max-w': spacing,
      }],

      // Height
      h: [{
        h: spacing,
      }],

      // Min-Height
      'min-h': [{
        'min-h': spacing,
      }],

      // Max-Height
      'max-h': [{
        'max-h': spacing,
      }],

      // Size
      size: [{
        size: spacing,
      }],

      // Flex Basis
      basis: [{
        basis: spacing
      }],

      // Flex Basis
      indent: [{
        indent: spacing
      }],

      // Scroll Margin
      'scroll-m': [{
        'scroll-m': spacing
      }],

      // Scroll Margin X
      'scroll-mx': [{
        'scroll-mx': spacing
      }],

      // Scroll Margin Y
      'scroll-my': [{
        'scroll-my': spacing
      }],

      // Scroll Margin Start
      'scroll-ms': [{
        'scroll-ms': spacing
      }],

      // Scroll Margin End
      'scroll-me': [{
        'scroll-me': spacing
      }],

      // Scroll Margin Top
      'scroll-mt': [{
        'scroll-mt': spacing
      }],

      // Scroll Margin Right
      'scroll-mr': [{
        'scroll-mr': spacing
      }],

      // Scroll Margin Bottom
      'scroll-mb': [{
        'scroll-mb': spacing
      }],

      // Scroll Margin Left
      'scroll-ml': [{
        'scroll-ml': spacing
      }],

      // Scroll Padding
      'scroll-p': [{
        'scroll-p': spacing
      }],

      // Scroll Padding X
      'scroll-px': [{
        'scroll-px': spacing
      }],

      // Scroll Padding Y
      'scroll-py': [{
        'scroll-py': spacing
      }],

      // Scroll Padding Start
      'scroll-ps': [{
        'scroll-ps': spacing
      }],

      // Scroll Padding End
      'scroll-pe': [{
        'scroll-pe': spacing
      }],

      // Scroll Padding Top
      'scroll-pt': [{
        'scroll-pt': spacing
      }],

      // Scroll Padding Right
      'scroll-pr': [{
        'scroll-pr': spacing
      }],

      // Scroll Padding Bottom
      'scroll-pb': [{
        'scroll-pb': spacing
      }],

      // Scroll Padding Left
      'scroll-pl': [{
        'scroll-pl': spacing
      }],

      // Box Shadow
      shadow: [{
        shadow: boxShadow
      }],

      //Font Size
      'font-size': [{
        text: fontSize
      }],

      // Font Family
      'font-family': [{
        font: fontFamily
      }],
    }
  }
});