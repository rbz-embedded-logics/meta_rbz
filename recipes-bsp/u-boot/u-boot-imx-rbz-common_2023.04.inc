DESCRIPTION = "i.MX U-Boot suppporting RBZ modules."

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://Licenses/gpl-2.0.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263"

UBOOT_SRC ?= "git://github.com/rbz-embedded-logics/uboot-imx-rbz.git;protocol=https"
SRCBRANCH = "lf_v2023.04"
SRC_URI = "${UBOOT_SRC};branch=${SRCBRANCH}"
LOCALVERSION ?= "-${SRCBRANCH}"
SRCREV = "c4db4512ecba2eda8bc41090635e777c746910e7"

DEPENDS += " \
    bc-native \
    bison-native \
    dtc-native \
    flex-native \
    gnutls-native \
    xxd-native \
    python3-native \
    python3-setuptools-native \
"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

inherit fsl-u-boot-localversion

BOOT_TOOLS = "imx-boot-tools"
