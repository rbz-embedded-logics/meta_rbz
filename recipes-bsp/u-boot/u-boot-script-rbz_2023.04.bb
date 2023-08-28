require recipes-bsp/u-boot/u-boot-imx-rbz-common_${PV}.inc

DEPENDS = "u-boot-mkimage-native"

inherit deploy

FILESEXTRAPATHS:prepend := "${THISDIR}/u-boot-rbz:"

SRC_URI:append = " \
  file://boot_mini.txt \
  file://boot_nano.txt \
  file://boot_plus.txt \
"
do_mkimage () {
    if [ "${MACHINE}" = "mod_imx8m_mini" ]
    then
      mkimage -T script -n bootscript -C none -d ${WORKDIR}/boot_mini.txt ${S}/boot.scr
    fi

    if [ "${MACHINE}" = "mod_imx8m_nano" ]
    then
      mkimage -T script -n bootscript -C none -d ${WORKDIR}/boot_nano.txt ${S}/boot.scr
    fi
      
    if [ "${MACHINE}" = "mod_imx8m_plus" ]
    then
      mkimage -T script -n bootscript -C none -d ${WORKDIR}/boot_plus.txt ${S}/boot.scr
    fi
    #uboot-mkimage -A arm -O linux -T script -C none -a 0 -e 0 \
                  #-n "boot script" -d ${BOOTSCRIPT} ${S}/boot.scr

    #uboot-mkimage -A arm -O linux -T script -C none -a 0 -e 0 \
                  #-n "upgrade script" -d ${UPGRADESCRIPT} ${S}/upgrade.scr
}

addtask mkimage after do_compile before do_install

do_compile[noexec] = "1"

do_install () {
    install -D -m 644 ${S}/boot.scr ${D}/boot.scr
}

do_deploy () {
    install -D -m 644 ${D}/boot.scr ${DEPLOYDIR}/boot.scr
}

addtask deploy after do_install before do_build

FILES:${PN} += "/"

COMPATIBLE_MACHINE = "(mx8-generic-bsp)"
