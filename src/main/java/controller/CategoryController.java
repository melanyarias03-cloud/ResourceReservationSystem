package controller;

import dataaccess.CategoryDataAccess;
import model.entity.Category;
import model.entity.User;
import view.AdminMenuView;
import view.CategoryView;
import java.util.List;
import model.logic.CategoryLogic;
import view.CategoryFormView;

public class CategoryController  {

    private final CategoryView view;
    private final User user;
    private final CategoryLogic categoryLogic;

    private final CategoryDataAccess categoryDataAccess;

    public CategoryController(
            CategoryView view,
            User user) {

        this.view = view;
        this.user = user;

        this.categoryDataAccess =new CategoryDataAccess();


        this.categoryLogic=new CategoryLogic();

        initializeEvents();
        loadCategories();
    }

    private void initializeEvents() {

        view.setSearchAction(e -> searchCategories());

        view.setBackAction(e -> back());

        view.setNewAction(e->openNewCategoryForm());

        view.setEditAction(e -> openEditCategoryForm());

        view.setDeleteAction(e->deleteCategory());


    }

    private void loadCategories() {

        try {

            List<Category> categories =
                    categoryDataAccess.findAll();

            view.showCategories(
                    categories
            );

        } catch (Exception e) {

            view.showError(
                    e.getMessage()
            );
        }
    }

    private void searchCategories() {

        try {

            String search =
                    view.getSearchInput();

            if (search.isEmpty()) {

                loadCategories();
                return;
            }

            List<Category> categories =
                    categoryDataAccess.findByDescription(search);

            view.showCategories(categories);

            view.showCategories(
                    categories
            );

        } catch (Exception e) {

            view.showError(
                    e.getMessage()
            );
        }
    }

    private void back() {

        view.dispose();

        AdminMenuView adminMenuView =
                new AdminMenuView();

        new AdminMenuController(
                adminMenuView,
                user
        );

        adminMenuView.setVisible(true);
    }
    private void openNewCategoryForm() {

        CategoryFormView formView =
                new CategoryFormView();

        formView.setSaveAction(
                e -> saveNewCategory(
                        formView
                )
        );

        formView.setCancelAction(
                e -> formView.dispose()
        );

        formView.setVisible(true);
    }
    private void saveNewCategory(
            CategoryFormView formView) {

        try {

            String description =
                    formView
                            .getDescriptionInput();

            int nextNumber =
                    categoryDataAccess
                            .getNextNumber();

            Category category =
                    categoryLogic
                            .createCategory(description,nextNumber);

            categoryDataAccess.save(
                    category
            );

            formView.showMessage(
                    "Category saved successfully."
            );

            formView.dispose();

            loadCategories();

        } catch (Exception e) {

            formView.showError(
                    e.getMessage()
            );
        }
    }
    private void openEditCategoryForm() {

        try {

            String id =
                    view.getSelectedCategoryId();

            if (id == null) {

                view.showError(
                        "Select a category first."
                );

                return;
            }

            Category category =
                    categoryDataAccess
                            .findById(id);

            if (category == null) {

                view.showError(
                        "Category not found."
                );

                return;
            }

            CategoryFormView formView =
                    new CategoryFormView();

            formView.setCategoryData(
                    category.getDescription()
            );

            formView.setSaveAction(
                    e -> updateCategory(
                            formView,
                            category
                    )
            );

            formView.setCancelAction(
                    e -> formView.dispose()
            );

            formView.setVisible(true);

        } catch (Exception e) {

            view.showError(
                    e.getMessage()
            );
        }
    }

    private void updateCategory(
            CategoryFormView formView,
            Category category) {

        try {

            String description =
                    formView
                            .getDescriptionInput();

            categoryLogic.updateCategory(
                    category,
                    description
            );

            categoryDataAccess.update(
                    category
            );

            formView.showMessage(
                    "Category updated successfully."
            );

            formView.dispose();

            loadCategories();

        } catch (Exception e) {

            formView.showError(
                    e.getMessage()
            );
        }
    }

    private void deleteCategory() {

        try {

            String id =
                    view.getSelectedCategoryId();

            if (id == null) {

                view.showError(
                        "Select a category first."
                );

                return;
            }

            boolean confirmed =
                    view.confirmDelete();

            if (!confirmed) {

                return;
            }

            categoryDataAccess.delete(
                    id
            );

            view.showMessage(
                    "Category deleted successfully."
            );

            loadCategories();

        } catch (Exception e) {

            view.showError(
                    e.getMessage()
            );
        }
    }
}
