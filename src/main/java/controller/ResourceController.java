package controller;

import dataaccess.CategoryDataAccess;
import dataaccess.ResourceDataAccess;
import model.entity.Category;
import model.entity.Resource;
import model.entity.User;
import view.AdminMenuView;
import view.ResourceView;
import model.logic.ResourceLogic;
import view.ResourceFormView;
import report.ResourceReport;
import java.util.List;

public class ResourceController {
    private final ResourceView view;
    private final User user;

    private final ResourceLogic resourceLogic;
    private final ResourceDataAccess resourceDataAccess;
    private final CategoryDataAccess categoryDataAccess;
    private final ResourceReport resourceReport;



    public ResourceController(
            ResourceView view,
            User user) {

        this.view = view;
        this.user = user;

        this.resourceDataAccess =
                new ResourceDataAccess();

        this.categoryDataAccess =
                new CategoryDataAccess();

        this.resourceLogic =
                new ResourceLogic();

        this.resourceReport =
                new ResourceReport();


        initializeEvents();

        loadCategories();
        loadResources();
    }

    private void initializeEvents() {

        view.setFilterAction(
                e -> filterResources()
        );

        view.setShowAllAction(
                e -> loadResources()
        );

        view.setGeneratePdfAction(
                e -> generatePdf()
        );

        view.setBackAction(e -> back());

        view.setNewAction(e -> openNewResourceForm());

        view.setEditAction(
                e -> openEditResourceForm());

        view.setDeleteAction(e->deleteResource());
    }

    private void loadResources() {

        try {

            List<Resource> resources =
                    resourceDataAccess
                            .findAll();

            view.showResources(
                    resources
            );

        } catch (Exception e) {

            view.showError(
                    e.getMessage()
            );
        }
    }

    private void loadCategories() {

        try {

            List<Category> categories =
                    categoryDataAccess
                            .findAll();

            view.showCategories(
                    categories
            );

        } catch (Exception e) {

            view.showError(
                    e.getMessage()
            );
        }
    }

    private void filterResources() {

        try {

            Category category =
                    view.getSelectedCategory();

            if (category == null) {

                view.showError(
                        "Select a category first."
                );

                return;
            }

            List<Resource> resources =
                    resourceDataAccess
                            .findByCategory(
                                    category.getId()
                            );

            view.showResources(
                    resources
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

    private void openNewResourceForm() {

        ResourceFormView formView =
                new ResourceFormView();

        try {

            List<Category> categories =
                    categoryDataAccess
                            .findAll();

            formView.showCategories(
                    categories
            );

            formView.setIdEditable(
                    true
            );

            formView.setSaveAction(
                    e -> saveNewResource(
                            formView
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


    private void saveNewResource(
            ResourceFormView formView) {

        try {

            String id =
                    formView
                            .getIdInput();

            Category category =
                    formView
                            .getSelectedCategory();

            String description =
                    formView
                            .getDescriptionInput();

            Resource resource =
                    resourceLogic
                            .createResource(
                                    id,
                                    category,
                                    description
                            );

            resourceDataAccess.save(
                    resource
            );

            formView.showMessage(
                    "Resource saved successfully."
            );

            formView.dispose();

            loadResources();

        } catch (Exception e) {

            formView.showError(
                    e.getMessage()
            );
        }
    }
    private void openEditResourceForm() {

        try {

            String id =
                    view.getSelectedResourceId();

            if (id == null) {

                view.showError(
                        "Select a resource first."
                );

                return;
            }

            Resource resource =
                    resourceDataAccess
                            .findById(id);

            if (resource == null) {

                view.showError(
                        "Resource not found."
                );

                return;
            }

            ResourceFormView formView =
                    new ResourceFormView();

            List<Category> categories =
                    categoryDataAccess
                            .findAll();

            formView.showCategories(
                    categories
            );

            formView.setResourceData(
                    resource.getId(),
                    resource.getCategory(),
                    resource.getDescription()
            );

            formView.setIdEditable(
                    false
            );

            formView.setSaveAction(
                    e -> updateResource(
                            formView,
                            resource
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
    private void updateResource(
            ResourceFormView formView,
            Resource resource) {

        try {

            Category category =
                    formView
                            .getSelectedCategory();

            String description =
                    formView
                            .getDescriptionInput();

            resourceLogic.updateResource(
                    resource,
                    category,
                    description
            );

            resourceDataAccess.update(
                    resource
            );

            formView.showMessage(
                    "Resource updated successfully."
            );

            formView.dispose();

            loadResources();

        } catch (Exception e) {

            formView.showError(
                    e.getMessage()
            );
        }
    }
    private void deleteResource() {

        try {

            String id =
                    view.getSelectedResourceId();

            if (id == null) {

                view.showError(
                        "Select a resource first."
                );

                return;
            }

            boolean confirmed =
                    view.confirmDelete();

            if (!confirmed) {

                return;
            }

            resourceDataAccess.delete(
                    id
            );

            view.showMessage(
                    "Resource deleted successfully."
            );

            loadResources();

        } catch (Exception e) {

            view.showError(
                    e.getMessage()
            );
        }
    }
    private void generatePdf() {

        try {

            List<Resource> resources =
                    resourceDataAccess.findAll();

            resourceReport.generate(
                    resources
            );

        } catch (Exception e) {

            view.showError(
                    e.getMessage()
            );
        }
    }

}
