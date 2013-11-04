__author__ = 'Ryan'
class Genre:
    def __init__(self, keyword, initialSong):
        self.keyword = keyword
        self.minimalList = initialSong.terms
        if keyword in self.minimalList:
            self.minimalList.remove(keyword)
        self.songCount = 0
        self.children = []
        self.rank = len(initialSong.keywords)

    def addSong(self, song):
        self.songCount += 1
        self.minListAdjust(song.terms)

    def minListAdjust(self, newList):
        for currents in self.minimalList:
            if (currents not in newList):
                self.minimalList.remove(currents)
                self.rank = len(self.minimalList)