# GNN-Based Network Intrusion Detection System

## Slide 1: Title
- **GNN-Based Network Intrusion Detection System**
- Detecting suspicious hosts in network traffic using graph learning
- Prepared for project presentation and live demonstration

## Slide 2: Introduction
- Modern networks face attacks such as DDoS, port scanning, botnet activity, and unauthorized access.
- Traditional intrusion detection often analyzes traffic records independently.
- Relationship-based attack patterns can be missed by flat record analysis.
- This project models traffic as a graph and applies a Graph Neural Network to learn both node features and neighborhood behavior.
- Goal: identify suspicious hosts more accurately and present results through an interactive dashboard.

## Slide 3: Literature Survey / Existing System
- Signature-based IDS detects known threats well but struggles with new or evolving attacks.
- Classical machine-learning models such as Decision Tree, Random Forest, SVM, and Logistic Regression treat each row independently.
- These methods usually ignore communication relationships between hosts.
- Existing systems can detect abnormal flow values, but coordinated graph-structured attacks remain difficult.
- A graph-based learning approach is needed for better detection of connected intrusion patterns.

## Slide 4: Problem Analysis
- Network attacks often involve patterns across multiple communicating hosts.
- Flat-record analysis misses neighborhood context and communication structure.
- Suspicious behavior may only become clear when flows are viewed as a connected graph.
- A GNN is suitable because it learns from both node attributes and graph connectivity.

## Slide 5: Requirements and Analysis
- Accept network traffic data in CSV format.
- Support logical fields such as source IP, destination IP, bytes, packets, duration, and label.
- Preprocess and normalize different column naming styles.
- Convert traffic data into a graph structure for training and inference.
- Support training and testing using Graph Neural Networks.
- Generate accuracy, precision, recall, F1-score, confusion matrix, and ROC curve.
- Provide a user-friendly interface for dataset preview and live inference.

## Slide 6: Proposed Methodology
- Collect or load network traffic CSV data.
- Normalize column names and labels into a standard format.
- Build a communication graph.
- Nodes represent IP addresses.
- Edges represent traffic flows between IPs.
- Aggregate node-level features from traffic behavior.
- Split graph nodes into training, validation, and testing sets.
- Train GNN models such as GCN, GraphSAGE, and GAT.
- Evaluate the trained model using classification metrics.
- Use the saved model to rank suspicious nodes during inference.
- Display results in a Streamlit dashboard.

## Slide 7: Process Flow
**CSV Data -> Preprocessing -> Graph Construction -> Feature Extraction -> GNN Training -> Evaluation -> Inference Dashboard**

## Slide 8: Hardware and Software Requirements
### Hardware Requirements
- Processor: Intel i5 or above
- RAM: 8 GB minimum
- Storage: 10 GB free space
- System: Laptop/Desktop

### Software Requirements
- Operating System: Windows / Linux / macOS
- Python 3.11
- Streamlit
- PyTorch
- PyTorch Geometric
- Pandas
- Scikit-learn
- Matplotlib
- Seaborn

### Development Tools
- VS Code / PyCharm
- Git
- Command Prompt / PowerShell

## Slide 9: Results
- The system successfully converts traffic flows into a graph representation.
- GNN models were trained for suspicious node classification.
- The project generated accuracy, precision, recall, F1-score, confusion matrix, and ROC curve.
- The dashboard supports synthetic dataset generation, dataset preview, and inference on uploaded CSV files.
- The model identified suspicious hosts and ranked them by suspicion score.
- Honest project note: the demonstrated trained model used synthetic traffic data for testing and validation.
- The system also supports real CSV traffic data such as CICIDS-style exports after column mapping.

## Slide 10: Screenshot Captions
- Training Metrics Dashboard
- Dataset Preview Screen
- Inference Output with Suspicious Source IPs
- Confusion Matrix and ROC Curve

## Slide 11: Live Demonstration
- Launch the Streamlit dashboard.
- Generate a synthetic traffic dataset.
- Preview normalized dataset columns.
- Run inference on CSV input.
- Display suspicious nodes and communication patterns.
- Show evaluation graphs and model metrics.

### Screenshots to Place
- Home dashboard screen
- Synthetic demo tab
- Dataset preview tab
- Inference tab
- Training curves
- Confusion matrix
- ROC curve

## Slide 12: Conclusion and Future Work
### Conclusion
- Graph-based modeling is effective for intrusion detection.
- Unlike traditional row-wise methods, this system uses communication relationships between hosts.
- GNN models improve the ability to detect suspicious nodes in connected network environments.
- The project also provides an interactive dashboard for training demonstration and inference.

### Future Work
- Train and validate on larger real-world datasets.
- Add temporal graph analysis for time-based attack behavior.
- Improve explainability of suspicious predictions.
- Support live packet capture and real-time intrusion detection.
- Deploy the system as a scalable security monitoring service.

## Slide 13: References
- CICIDS2017 Dataset, Canadian Institute for Cybersecurity
- PyTorch Official Documentation
- PyTorch Geometric Official Documentation
- Scikit-learn Official Documentation
- Streamlit Official Documentation
- Research papers on Graph Neural Networks for Intrusion Detection

## Presenter Notes
- Keep the story simple: traffic records become a graph, then the GNN learns suspicious behavior from both features and connections.
- Emphasize why graph structure matters more than row-wise classification alone.
- During the demo, show dataset preview first, then training metrics, then inference output.
- If asked about realism, clearly state that synthetic data was used for the demo and that the pipeline also supports real CICIDS-style CSVs after column mapping.
