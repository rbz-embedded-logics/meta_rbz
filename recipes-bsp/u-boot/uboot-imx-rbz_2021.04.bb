# Copyright (C) 2013-2016 Freescale Semiconductor
# Copyright 2018 (C) O.S. Systems Software LTDA.
# Copyright 2017-2021 NXP

require recipes-bsp/u-boot/u-boot.inc

DESCRIPTION = "i.MX U-Boot suppporting RBZ modules."

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/gpl-2.0.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263"

DEPENDS += "flex-native bison-native bc-native dtc-native u-boot-mkimage-native"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

inherit fsl-u-boot-localversion

BOOT_TOOLS = "imx-boot-tools"

PROVIDES += "u-boot"

UBOOT_SRC ?= "git://github.com/rbz-embedded-logics/uboot-imx-rbz.git;protocol=https"
SRCBRANCH = "main"
SRC_URI = "${UBOOT_SRC};branch=${SRCBRANCH}"
SRCREV = "28fc0e442c2e2b986d35ea8703847785df45697d"

FILESEXTRAPATHS_prepend := "${THISDIR}/u-boot-rbz:"

SRC_URI_append = " \
  file://boot_mini.txt \
  file://boot_nano.txt \
  file://boot_plus.txt \
"

LOCALVERSION = "-${SRCBRANCH}"

do_deploy_append_mx8m() {
    # Deploy u-boot-nodtb.bin and fsl-imx8m*-XX.dtb for mkimage to generate boot binary
    if [ -n "${UBOOT_CONFIG}" ]
    then
        for config in ${UBOOT_MACHINE}; do
            i=$(expr $i + 1);
            for type in ${UBOOT_CONFIG}; do
                j=$(expr $j + 1);
                if [ $j -eq $i ]
                then
                    install -d ${DEPLOYDIR}/${BOOT_TOOLS}
                    install -m 0777 ${B}/${config}/arch/arm/dts/${UBOOT_DTB_NAME}  ${DEPLOYDIR}/${BOOT_TOOLS}
                    install -m 0777 ${B}/${config}/u-boot-nodtb.bin  ${DEPLOYDIR}/${BOOT_TOOLS}/u-boot-nodtb.bin-${MACHINE}-${type}
                fi
            done
            unset  j
        done
        unset  i
    fi

    if [ "${MACHINE}" = "mod_imx8m_mini" ]
    then
      mkimage -T script -n bootscript -C none -d ${WORKDIR}/boot_mini.txt ${DEPLOYDIR}/boot.scr
    fi

    if [ "${MACHINE}" = "mod_imx8m_nano" ]
    then
      mkimage -T script -n bootscript -C none -d ${WORKDIR}/boot_nano.txt ${DEPLOYDIR}/boot.scr
    fi
      
    if [ "${MACHINE}" = "mod_imx8m_plus" ]
    then
      mkimage -T script -n bootscript -C none -d ${WORKDIR}/boot_plus.txt ${DEPLOYDIR}/boot.scr
    fi
}

UBOOT_TAGGED_BINARY ?= "u-boot-tagged.${UBOOT_SUFFIX}"

deploy_tag() {
    # Append a tag to the bootloader image used in the SD card image
    cp ${UBOOT_BINARY} ${UBOOT_TAGGED_BINARY}
    ln -sf ${UBOOT_TAGGED_BINARY} ${UBOOT_BINARY}
    stat -L -cUUUBURNXXOEUZX7+A-XY5601QQWWZ%sEND ${UBOOT_BINARY} >> ${UBOOT_BINARY}
}

do_deploy_append_mx6() {
    deploy_tag
}

do_deploy_append_mx7() {
    deploy_tag
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx6|mx7|mx8)"

UBOOT_NAME_mx6 = "u-boot-${MACHINE}.bin-${UBOOT_CONFIG}"
UBOOT_NAME_mx7 = "u-boot-${MACHINE}.bin-${UBOOT_CONFIG}"
UBOOT_NAME_mx8 = "u-boot-${MACHINE}.bin-${UBOOT_CONFIG}"
