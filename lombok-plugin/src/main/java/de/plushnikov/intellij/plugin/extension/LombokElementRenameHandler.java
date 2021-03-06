package de.plushnikov.intellij.plugin.extension;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.refactoring.RefactoringBundle;
import com.intellij.refactoring.rename.RenameHandler;
import com.intellij.refactoring.util.CommonRefactoringUtil;
import de.plushnikov.intellij.plugin.psi.LombokLightMethodBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * "Rename"-Handler for methods generated by lombok
 * At this moment it doesn't renamed anything, but forbid default rename operation
 */
public class LombokElementRenameHandler implements RenameHandler {
  public boolean isAvailableOnDataContext(DataContext dataContext) {
    final PsiElement element = LangDataKeys.PSI_ELEMENT.getData(dataContext);
    return element instanceof LombokLightMethodBuilder;
  }

  public boolean isRenaming(DataContext dataContext) {
    return isAvailableOnDataContext(dataContext);
  }

  public void invoke(@NotNull Project project, Editor editor, PsiFile file, @Nullable DataContext dataContext) {
    invokeInner(project, editor);
  }

  public void invoke(@NotNull Project project, @NotNull PsiElement[] elements, @Nullable DataContext dataContext) {
    Editor editor = dataContext == null ? null : PlatformDataKeys.EDITOR.getData(dataContext);
    invokeInner(project, editor);
  }

  private void invokeInner(Project project, Editor editor) {
    CommonRefactoringUtil.showErrorHint(project, editor,
        RefactoringBundle.getCannotRefactorMessage("This element cannot be renamed."),
        RefactoringBundle.message("rename.title"), null);
  }
}
