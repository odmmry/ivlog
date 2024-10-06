package io.github.devhyper.openvideoeditor.videoeditor

import androidx.lifecycle.ViewModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class VideoEditorViewModel : ViewModel() {
    val transformManager = TransformManager()

    private val _outputPath = MutableStateFlow("")
    val outputPath: StateFlow<String> = _outputPath.asStateFlow()

    private val _projectOutputPath = MutableStateFlow("")
    val projectOutputPath: StateFlow<String> = _projectOutputPath.asStateFlow()

    private val _controlsVisible = MutableStateFlow(true)
    val controlsVisible: StateFlow<Boolean> = _controlsVisible.asStateFlow()

    private val _projectSavingSupported = MutableStateFlow(true)
    val projectSavingSupported: StateFlow<Boolean> = _projectSavingSupported.asStateFlow()

    private val _filterDurationEditorEnabled = MutableStateFlow(false)
    val filterDurationEditorEnabled = _filterDurationEditorEnabled.asStateFlow()

    private val _filterDurationCallback = MutableStateFlow<(LongRange) -> Unit> {}
    val filterDurationCallback: StateFlow<(LongRange) -> Unit> =
        _filterDurationCallback.asStateFlow()

    private val _filterDurationEditorSliderPosition = MutableStateFlow(0f..0f)
    val filterDurationEditorSliderPosition: StateFlow<ClosedFloatingPointRange<Float>> =
        _filterDurationEditorSliderPosition.asStateFlow()

    private val _startFilterSelected = MutableStateFlow(true)
    val startFilterSelected: StateFlow<Boolean> = _startFilterSelected.asStateFlow()

    private val _filterDialogArgs = MutableStateFlow<PersistentList<EffectDialogSetting>>(
        persistentListOf()
    )
    val filterDialogArgs: StateFlow<PersistentList<EffectDialogSetting>> =
        _filterDialogArgs.asStateFlow()

    private val _currentEditingEffect = MutableStateFlow<OnVideoUserEffect?>(null)
    val currentEditingEffect: StateFlow<OnVideoUserEffect?> =
        _currentEditingEffect.asStateFlow()

    fun setOutputPath(path: String) {
        _outputPath.update { path }
    }

    fun setProjectOutputPath(path: String) {
        _projectOutputPath.update { path }
    }

    fun setControlsVisible(value: Boolean) {
        _controlsVisible.update { value }
    }

    fun setProjectSavingSupported(value: Boolean) {
        _projectSavingSupported.update { value }
    }

    fun setFilterDurationEditorEnabled(value: Boolean) {
        _filterDurationEditorEnabled.update { value }
    }

    fun setFilterDurationCallback(value: (LongRange) -> Unit) {
        _filterDurationCallback.update { value }
    }

    fun setFilterDurationEditorSliderPosition(value: ClosedFloatingPointRange<Float>) {
        _filterDurationEditorSliderPosition.update { value }
    }

    fun setStartFilterSelected(value: Boolean) {
        _startFilterSelected.update { value }
    }

    fun setFilterDialogArgs(value: PersistentList<EffectDialogSetting>) {
        _filterDialogArgs.update { value }
    }

    fun setCurrentEditingEffect(value: OnVideoUserEffect?) {
        _currentEditingEffect.update { value }
    }
}
