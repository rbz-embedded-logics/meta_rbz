FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_mx8mp = " \
 file://0001-Add-MOD_IMX8M_PLUS-support.patch \
 file://0002-fix-dtb-errors.patch \
 file://0003-add-uart-support.patch \
 file://0004-add-adc-ads7828-support.patch \
 file://0005-add-flexcan-support.patch \
 file://0006-add-rbz-carrier-basic.patch \
 file://0007-add-lvds-and-tpm-support.patch \
"
