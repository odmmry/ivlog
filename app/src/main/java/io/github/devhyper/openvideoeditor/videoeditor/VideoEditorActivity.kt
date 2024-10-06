package io.github.devhyper.openvideoeditor.videoeditor

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.devhyper.openvideoeditor.R
import io.github.devhyper.openvideoeditor.misc.PROJECT_MIME_TYPE
import io.github.devhyper.openvideoeditor.misc.setImmersiveMode
import io.github.devhyper.openvideoeditor.misc.setupSystemUi


class VideoEditorActivity : ComponentActivity() {
    private lateinit var createDocument: ActivityResultLauncher<String>
    private lateinit var createProject: ActivityResultLauncher<String>
    private lateinit var requestVideoPermission: ActivityResultLauncher<String>
    private lateinit var viewModel: VideoEditorViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupSystemUi()

        viewModel = VideoEditorViewModel()

        window.decorView.setOnSystemUiVisibilityChangeListener {
            viewModel.setControlsVisible(it == 0)
        }

        createDocument = registerForActivityResult(
            ActivityResultContracts.CreateDocument(
                "video/mp4"
            )
        ) { uri ->
            if (uri != null) {
                viewModel.setOutputPath(uri.toString())
            }
        }
        createProject = registerForActivityResult(
            ActivityResultContracts.CreateDocument(
                PROJECT_MIME_TYPE
            )
        ) { uri ->
            if (uri != null) {
                viewModel.setProjectOutputPath(uri.toString())
            }
        }
        requestVideoPermission =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                if (it) {
                    recreate()
                } else {
                    val text = getString(R.string.permission_denied_grant_video_permissions)
                    val duration = Toast.LENGTH_SHORT

                    val toast = Toast.makeText(this, text, duration)
                    toast.show()
                }
            }

        var uri: String? = null
        if (intent.action == Intent.ACTION_EDIT || intent.action == Intent.ACTION_VIEW) {
            intent.dataString?.let {
                uri = it
            }
        } else if (intent.action == Intent.ACTION_SEND) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                (intent.getParcelableExtra(Intent.EXTRA_STREAM, Uri::class.java))?.let {
                    uri = it.toString()
                }
            } else {
                @Suppress("DEPRECATION")
                (intent.getParcelableExtra(Intent.EXTRA_STREAM) as? Uri)?.let {
                    uri = it.toString()
                }
            }
        }

        uri?.let {
            setContent {
                viewModel = viewModel { viewModel }
                val controlsVisible by viewModel.controlsVisible.collectAsState()
                setImmersiveMode(!controlsVisible)
                VideoEditorScreen(it, createDocument, createProject, requestVideoPermission)
            }
        } ?: finish()
    }
}
