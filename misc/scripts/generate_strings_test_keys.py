#!/usr/bin/env python3
"""Generate an asset JSON array containing keys from strings_test.xml."""

from __future__ import annotations

import argparse
import json
from pathlib import Path
import xml.etree.ElementTree as ET


REPO_ROOT = Path(__file__).resolve().parents[2]
DEFAULT_INPUT = REPO_ROOT / "app/src/main/res/values/strings_test.xml"
DEFAULT_OUTPUT = REPO_ROOT / "app/src/main/assets/strings_test_keys.json"


def parse_string_keys(input_path: Path) -> list[str]:
    tree = ET.parse(input_path)
    root = tree.getroot()
    return [
        element.attrib["name"]
        for element in root.findall("string")
        if "name" in element.attrib
    ]


def main() -> None:
    parser = argparse.ArgumentParser(
        description="Parse strings_test.xml and write a JSON array of string keys."
    )
    parser.add_argument(
        "--input",
        type=Path,
        default=DEFAULT_INPUT,
        help=f"Input strings XML file. Default: {DEFAULT_INPUT}",
    )
    parser.add_argument(
        "--output",
        type=Path,
        default=DEFAULT_OUTPUT,
        help=f"Output asset JSON file. Default: {DEFAULT_OUTPUT}",
    )
    args = parser.parse_args()

    keys = parse_string_keys(args.input)
    args.output.parent.mkdir(parents=True, exist_ok=True)
    args.output.write_text(
        json.dumps(keys, indent=2, ensure_ascii=False) + "\n",
        encoding="utf-8",
    )
    print(f"Wrote {len(keys)} keys to {args.output}")


if __name__ == "__main__":
    main()
