public class PatientBSTree {
    private PatientInfo root;

    public PatientBSTree() {
        root = null;
    }

    public void insertPatient(String name, String age, String email, String bloodType) {
        root = insertRec(root, name, age, email, bloodType);
    }

    private PatientInfo insertRec(PatientInfo root, String name, String age, String email, String bloodType) {
        if (root == null) {
            return new PatientInfo(name, age, email, bloodType);
        }
        if (name.compareToIgnoreCase(root.name) < 0) {
            root.left = insertRec(root.left, name, age, email, bloodType);
        } else if (name.compareToIgnoreCase(root.name) > 0) {
            root.right = insertRec(root.right, name, age, email, bloodType);
        }
        return root;
    }

    public PatientInfo searchPatient(String name) {
        return searchRec(root, name);
    }

    private PatientInfo searchRec(PatientInfo root, String name) {
        if (root == null || root.name.equalsIgnoreCase(name)) {
            return root;
        }
        if (name.compareToIgnoreCase(root.name) < 0) {
            return searchRec(root.left, name);
        } else {
            return searchRec(root.right, name);
        }
    }

    public void deletePatient(String name) {
        root = deleteRec(root, name);
    }

    private PatientInfo deleteRec(PatientInfo root, String name) {
        if (root == null) return null;

        if (name.compareToIgnoreCase(root.name) < 0) {
            root.left = deleteRec(root.left, name);
        } else if (name.compareToIgnoreCase(root.name) > 0) {
            root.right = deleteRec(root.right, name);
        } else {
            // Node with one or no child
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;

            // Node with two children: Get inorder successor (smallest in the right subtree)
            PatientInfo min = minValueNode(root.right);
            root.name = min.name;
            root.age = min.age;
            root.email = min.email;
            root.bloodType = min.bloodType;

            // Delete the inorder successor
            root.right = deleteRec(root.right, min.name);
        }

        return root;
    }

    private PatientInfo minValueNode(PatientInfo node) {
        PatientInfo current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    // Alias method to match EditInfoForm
    public PatientInfo searchPatientByName(String name) {
        return searchPatient(name);
    }
}
