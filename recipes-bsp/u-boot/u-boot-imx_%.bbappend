# U-boot Makefile seems to have a race when building in parallel (make -j):
# Creating symlink to arch include directory and building target u-boot.lds (which requires this symlink) seems to overlap sometimes, resulting in:
#  /some/path/imx8mm_evk.h:11:10: fatal error: asm/arch/imx-regs.h: No such file or directory
PARALLEL_MAKE = ""
PARALLEL_MAKEINST = ""

FILESEXTRAPATHS_prepend := "${THISDIR}/u-boot-imx:"
SRC_URI_append = " file://0001-Add-MOD_IMX8M_PLUS-support.patch"

