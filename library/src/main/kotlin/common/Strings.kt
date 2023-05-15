package dev.eyrond.paperkt.common

internal object Strings {

    private const val BANNER = """
        
        
        ────────────────────────────────────────────────────────────────────────────────────────────────────────────────
        ─██████████████─██████████████─██████████████─██████████████─████████████████───██████──████████─██████████████─
        ─██░░░░░░░░░░██─██░░░░░░░░░░██─██░░░░░░░░░░██─██░░░░░░░░░░██─██░░░░░░░░░░░░██───██░░██──██░░░░██─██░░░░░░░░░░██─
        ─██░░██████░░██─██░░██████░░██─██░░██████░░██─██░░██████████─██░░████████░░██───██░░██──██░░████─██████░░██████─
        ─██░░██──██░░██─██░░██──██░░██─██░░██──██░░██─██░░██─────────██░░██────██░░██───██░░██──██░░██───────██░░██─────
        ─██░░██████░░██─██░░██████░░██─██░░██████░░██─██░░██████████─██░░████████░░██───██░░██████░░██───────██░░██─────
        ─██░░░░░░░░░░██─██░░░░░░░░░░██─██░░░░░░░░░░██─██░░░░░░░░░░██─██░░░░░░░░░░░░██───██░░░░░░░░░░██───────██░░██─────
        ─██░░██████████─██░░██████░░██─██░░██████████─██░░██████████─██░░██████░░████───██░░██████░░██───────██░░██─────
        ─██░░██─────────██░░██──██░░██─██░░██─────────██░░██─────────██░░██──██░░██─────██░░██──██░░██───────██░░██─────
        ─██░░██─────────██░░██──██░░██─██░░██─────────██░░██████████─██░░██──██░░██████─██░░██──██░░████─────██░░██─────
        ─██░░██─────────██░░██──██░░██─██░░██─────────██░░░░░░░░░░██─██░░██──██░░░░░░██─██░░██──██░░░░██─────██░░██─────
        ─██████─────────██████──██████─██████─────────██████████████─██████──██████████─██████──████████─────██████─────
        ────────────────────────────────────────────────────────────────────────────────────────────────────────────────
        PaperKt %s © Eyrond
        Website: https://paperkt.eyrond.dev
        GitHub: https://github.com/eyrond/paperkt
        
    """

    fun formattedBanner(version: String): String {
        return BANNER.format(version)
    }
}
