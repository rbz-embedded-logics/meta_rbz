NNSHARK_SRC ?= "git://github.com/nxp-imx/nnshark.git;protocol=https"
SRCBRANCH ?= "2021.10.imx"
SRC_URI = " \
          ${NNSHARK_SRC};branch=${SRCBRANCH};name=base \
          git://gitlab.freedesktop.org/gstreamer/common.git;protocol=https;destsuffix=git/common;name=common;;branch=master \
          "

SRCREV_base = "e905828f16e3c374da7da9af30a5922086da4400"
SRCREV_common = "b64f03f6090245624608beb5d2fff335e23a01c0"
SRCREV_FORMAT = "base_common"
