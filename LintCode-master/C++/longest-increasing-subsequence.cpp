// Time:  O(nlogn)
// Space: O(n)

class Solution {
public:
    /**
     * @param nums: The integer array
     * @return: The length of LIS (longest increasing subsequence)
     */
    void insert(vector<int>& LIS, int target) {
        int left = 0, right = LIS.size() - 1;
        auto comp = [](int x, int target) { return x <= target; };

        // Find the largest index "left" which satisfies LIS[left] <= target
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (!comp(LIS[mid], target)) {
                right = mid - 1;
            }
            else {
                left = mid + 1;
            }
        }

        // If not found, append the target.
        if (left == LIS.size()) {
            LIS.emplace_back(target);
        }
        else {
            LIS[left] = target;
        }
    }

    int longestIncreasingSubsequence(vector<int> nums) {
        vector<int> LIS;

        for (auto& i : nums) {
            insert(LIS, i);
        }

        return LIS.size();
    }
};

